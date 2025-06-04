package com.experiment.daynight

import android.media.MediaPlayer
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.launch
import kotlin.math.hypot


@Composable
fun ThemeToggleButton(
    modifier: Modifier = Modifier,
    soundEnabled: Boolean = true,
    onThemeToggle: ((Boolean) -> Unit)? = null
) {
    val context = LocalContext.current
    val systemDark = isSystemInDarkTheme()
    var isDark by remember { mutableStateOf(systemDark) }
    var toggleOffset by remember { mutableStateOf(Offset.Zero) }
    var containerSize by remember { mutableStateOf(Size.Zero) }
    var animationTrigger by remember { mutableStateOf(false) }

    val radius = remember { Animatable(0f) }
    val overlayColor = if (isDark) Color.White else Color.Black
    val baseColor = if (isDark) Color.Black else Color.White

    val scope = rememberCoroutineScope()

    val soundPlayer = remember {
        if (soundEnabled) {
            MediaPlayer.create(context, android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
        } else null
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned {
                containerSize = it.size.toSize()
            }
            .background(baseColor)
    ) {
        if (animationTrigger) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = overlayColor,
                    center = toggleOffset,
                    radius = radius.value
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isDark) "Dark Mode" else "Light Mode",
                style = MaterialTheme.typography.headlineMedium,
                color = if (isDark) Color.White else Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(if (isDark) Color.Gray else Color.DarkGray)
                    .onGloballyPositioned { layoutCoordinates ->
                        val pos = layoutCoordinates.localToRoot(Offset.Zero)
                        toggleOffset = Offset(
                            pos.x + layoutCoordinates.size.width / 2f,
                            pos.y + layoutCoordinates.size.height / 2f
                        )
                    }
                    .clickable {
                        val maxRadius = containerSize.getDistanceToCorner(toggleOffset)

                        soundPlayer?.start()

                        animationTrigger = true
                        scope.launch {
                            radius.snapTo(0f)
                            radius.animateTo(
                                targetValue = maxRadius,
                                animationSpec = tween(600, easing = FastOutSlowInEasing)
                            )
                            isDark = !isDark
                            onThemeToggle?.invoke(isDark)
                            animationTrigger = false
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (!isDark) "🌙" else "☀️",
                    fontSize = 24.sp,
                    color = Color.White
                )
            }
        }
    }
}

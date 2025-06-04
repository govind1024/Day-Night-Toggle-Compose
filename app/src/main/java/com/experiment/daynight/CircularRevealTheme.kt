package com.experiment.daynight

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.launch
import kotlin.math.hypot

@Composable
fun CircularThemeReveal() {
    var isDark by remember { mutableStateOf(false) }
    var toggleOffset by remember { mutableStateOf(Offset.Zero) }
    var containerSize by remember { mutableStateOf(Size.Zero) }
    var animationTrigger by remember { mutableStateOf(false) }

    // Theme switching logic
    val animationDirection = rememberUpdatedState(if (isDark) -1 else 1)

    val radius = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    // Colors
    val baseColor = if (isDark) Color.Black else Color.White
    val overlayColor = if (isDark) Color.White else Color.Black

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                containerSize = it.size.toSize()
            }
            .background(baseColor)
    ) {
        // Draw animated circle
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

            Spacer(modifier = Modifier.height(80.dp))

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(if (isDark) Color.Gray else Color.DarkGray)
                    .onGloballyPositioned { layoutCoordinates ->
                        val position = layoutCoordinates.localToRoot(Offset.Zero)
                        toggleOffset = Offset(
                            position.x + layoutCoordinates.size.width / 2f,
                            position.y + layoutCoordinates.size.height / 2f
                        )
                    }
                    .clickable {
                        val maxRadius = containerSize.getDistanceToCorner(toggleOffset)

                        animationTrigger = true
                        scope.launch {
                            if (animationDirection.value > 0) {
                                // Light → Dark (expand)
                                radius.snapTo(0f)
                                radius.animateTo(
                                    targetValue = maxRadius,
                                    animationSpec = tween(600, easing = FastOutSlowInEasing)
                                )
                            } else {
                                // Dark → Light (contract)
                                radius.snapTo(maxRadius)
                                radius.animateTo(
                                    targetValue = 0f,
                                    animationSpec = tween(600, easing = FastOutLinearInEasing)
                                )
                            }

                            // Toggle theme only after animation
                            isDark = !isDark
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


/*private fun androidx.compose.ui.geometry.Size.getDistanceToCorner(from: Offset): Float {
    val distances = listOf(
        Offset(0f, 0f),
        Offset(width, 0f),
        Offset(0f, height),
        Offset(width, height)
    ).map { offset ->
        val dx = from.x - offset.x
        val dy = from.y - offset.y
        kotlin.math.sqrt(dx * dx + dy * dy)
    }

    return distances.maxOrNull() ?: 0f
}*/

fun Size.getDistanceToCorner(from: Offset): Float {
    val corners = listOf(
        Offset(0f, 0f),
        Offset(width, 0f),
        Offset(0f, height),
        Offset(width, height)
    )
    return corners.maxOf { hypot((from.x - it.x), (from.y - it.y)) }
}

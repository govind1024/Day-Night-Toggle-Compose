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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.hypot

@Composable
fun CircularThemeReveal() {
    // State to track whether the theme is dark or light.
    var isDark by remember { mutableStateOf(false) }
    // State to store the center offset of the toggle button, used as the origin for the reveal animation.
    var toggleOffset by remember { mutableStateOf(Offset.Zero) }
    // State to store the size of the container, used to calculate the maximum radius of the reveal.
    var containerSize by remember { mutableStateOf(Size.Zero) }
    // State to trigger the reveal animation.
    var animationTrigger by remember { mutableStateOf(false) }

    // Determines the direction of the animation.
    // -1 for contracting (dark to light), 1 for expanding (light to dark).
    // `rememberUpdatedState` ensures that the latest value of `isDark` is used,
    // even if the coroutine that uses it is launched with an older value.
    val animationDirection = rememberUpdatedState(if (isDark) -1 else 1)

    // Animatable for the radius of the circular reveal.
    val radius = remember { Animatable(0f) }
    // Coroutine scope for launching animations.
    val scope = rememberCoroutineScope()

    // Define base and overlay colors based on the current theme (dark/light).
    // The base color is the background color of the screen.
    // The overlay color is the color of the reveal circle.
    val baseColor = if (isDark) Color.Black else Color.White
    val overlayColor = if (isDark) Color.White else Color.Black

    Box(
        modifier = Modifier
            .fillMaxSize()
            // Get the size of the container once it's laid out.
            .onGloballyPositioned {
                containerSize = it.size.toSize()
            }
            .background(baseColor) // Set the background color of the Box.
    ) {
        // Draw the animated circle only when animationTrigger is true.
        if (animationTrigger) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = overlayColor,
                    center = toggleOffset, // Center of the circle is the toggle button's center.
                    radius = radius.value // Radius is animated.
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display "Dark Mode" or "Light Mode" text based on the current theme.
            Text(
                text = if (isDark) "Dark Mode" else "Light Mode",
                style = MaterialTheme.typography.headlineMedium,
                color = if (isDark) Color.White else Color.Black // Text color contrasts with the background.
            )

            Spacer(modifier = Modifier.height(140.dp))

            // Toggle button.
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape) // Clip to a circular shape.
                    .background(if (isDark) Color.Gray else Color.DarkGray) // Background color of the button.
                    // Get the position of the toggle button once it's laid out.
                    .onGloballyPositioned { layoutCoordinates ->
                        // Convert local coordinates to root (screen) coordinates.
                        val position = layoutCoordinates.localToRoot(Offset.Zero)
                        // Calculate the center of the toggle button.
                        toggleOffset = Offset(
                            position.x + layoutCoordinates.size.width / 2f,
                            position.y + layoutCoordinates.size.height / 2f
                        )
                    }
                    .clickable {
                        // Calculate the maximum radius needed for the reveal to cover the entire screen.
                        // This is the distance from the toggle button's center to the furthest corner of the screen.
                        val maxRadius = containerSize.getDistanceToCorner(toggleOffset)

                        animationTrigger = true // Start the animation.
                        scope.launch {
                            if (animationDirection.value > 0) { // Current: Light, Target: Dark (expand reveal)
                                // Start the radius from 0.
                                radius.snapTo(0f)

                                // Animate the radius expanding to cover the screen.
                                radius.animateTo(
                                    targetValue = maxRadius,
                                    animationSpec = tween(1000, easing = FastOutSlowInEasing)
                                )

                                // Toggle the theme after the expansion animation completes.
                                isDark = !isDark
                            } else { // Current: Dark, Target: Light (contract/conceal reveal)

                                // Toggle the theme FIRST to show the light theme underneath the contracting dark circle.
                                isDark = !isDark

                                // Delay for one frame to allow recomposition with the new theme colors.
                                // This ensures the light theme is visible as the dark circle contracts.
                                delay(16) // Approximately 1 frame at 60fps.

                                // Start the radius from the maximum size.
                                radius.snapTo(maxRadius)

                                // Animate the radius contracting to 0.
                                radius.animateTo(
                                    targetValue = 0f,
                                    animationSpec = tween(2000, easing = FastOutLinearInEasing)
                                )
                            }

                            animationTrigger = false // Stop drawing the circle after animation.
                        }

                    },
                contentAlignment = Alignment.Center // Center the icon within the button.
            ) {
                // Display a moon or sun icon based on the current theme.
                Text(
                    text = if (!isDark) "🌙" else "☀️", // Moon for light mode, Sun for dark mode.
                    fontSize = 24.sp,
                    color = Color.White // Icon color.
                )
            }
        }
    }
}

/**
 * Extension function for [Size] to calculate the maximum distance from a given [Offset] (point)
 * within the bounds of this Size to any of its four corners.
 *
 * This is used to determine the radius required for a circular reveal animation
 * to completely cover the area defined by this Size, originating from the [from] offset.
 *
 * @param from The [Offset] from which to measure the distance to the corners.
 * @return The maximum distance to any corner, as a [Float].
 */
fun Size.getDistanceToCorner(from: Offset): Float {
    // Define the four corners of the rectangle represented by this Size.
    val corners = listOf(
        Offset(0f, 0f),         // Top-left
        Offset(width, 0f),      // Top-right
        Offset(0f, height),     // Bottom-left
        Offset(width, height)   // Bottom-right
    )
    // Calculate the distance from the 'from' point to each corner using the Pythagorean theorem (hypot).
    // Return the maximum of these distances.
    return corners.maxOf { corner -> hypot((from.x - corner.x), (from.y - corner.y)) }
}

package com.experiment.daynight.utils

import android.util.Size
import androidx.compose.ui.geometry.Offset
import kotlin.math.hypot

fun Size.getDistanceToCorner(from: Offset): Float {
    val distances = listOf(
        Offset(0f, 0f),
        Offset(width.toFloat(), 0f),
        Offset(0f, height.toFloat()),
        Offset(width.toFloat(), height.toFloat())
    ).map { hypot((from.x - it.x), (from.y - it.y)) }

    return distances.maxOrNull() ?: 0f
}

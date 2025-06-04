package com.experiment.daynight

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ThemeToggleSwitch(isDark: Boolean, onToggle: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = if (isDark) "Dark Mode" else "Light Mode", color = if(isDark) Color.White else Color.Black)
        Spacer(modifier = Modifier.width(12.dp))
        Switch(
            checked = isDark,
            onCheckedChange = { onToggle() }
        )
    }
}
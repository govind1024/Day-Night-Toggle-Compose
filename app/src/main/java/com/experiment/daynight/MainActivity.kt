package com.experiment.daynight

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                ThemeToggleButton(
                    soundEnabled = true
                ) { isDark ->
                    // Optional: trigger your app theme switch logic
                    Log.d("Theme", "Dark mode is now: $isDark")
                }
            }
            //AppThemeSwitcherApp()
            //CircularThemeReveal()
        }
    }
}







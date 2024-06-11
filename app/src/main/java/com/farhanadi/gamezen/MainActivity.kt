package com.farhanadi.gamezen

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.farhanadi.gamezen.NavUtils
import com.farhanadi.gamezen.ui.theme.DarkPurple

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                // Set the status bar color
                val statusBarColor = DarkPurple
                setStatusBarColor(statusBarColor)

                // Call your NavUtils composable
                NavUtils()
            }
        }
    }

    @Composable
    private fun setStatusBarColor(color: Color) {
        DisposableEffect(color) {
            // Set status bar color
            WindowCompat.setDecorFitsSystemWindows(window, true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = color.toArgb()
            }
            onDispose { }
        }
    }
}

/*

  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FishINTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavigationUtils()
                }
            }
        }
    }
}
 */

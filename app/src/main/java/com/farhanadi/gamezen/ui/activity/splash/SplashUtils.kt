package com.farhanadi.gamezen.ui.activity.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.farhanadi.gamezen.R
import kotlinx.coroutines.delay

@Composable
fun SplashUtils(
    navigateToMain: () -> Unit
) {
    val painter = rememberImagePainter(data = R.drawable.gamezen_logosplash)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF18122B))
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(350.dp)
        )
    }

    LaunchedEffect(key1 = true) {
        delay(3000)
        navigateToMain()
    }
}

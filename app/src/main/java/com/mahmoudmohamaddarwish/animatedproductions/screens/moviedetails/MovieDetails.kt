package com.mahmoudmohamaddarwish.animatedproductions.screens.moviedetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme.AnimatedProductionsTheme

class MovieDetails : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimatedProductionsTheme {
            }
        }
    }
}


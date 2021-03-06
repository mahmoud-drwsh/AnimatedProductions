package com.mahmoudmohamaddarwish.animatedproductions.screens.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Fills max available space and shows a message in the center
 * */
@Composable
fun CenteredText(
    text: String,
    modifier: Modifier = Modifier,
) {
    CenteredContent(modifier) {
        Text(text)
    }
}
package com.mahmoudmohamaddarwish.animatedproductions.screens.home.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
fun MainAppBar(title: @Composable () -> Unit) {
    CenterAlignedTopAppBar(
        title = title,
        actions = {
            SortDialog()
        },
        modifier = Modifier
            .padding(8.dp)
            .shadow(8.dp, RoundedCornerShape(8.dp))
    )
}
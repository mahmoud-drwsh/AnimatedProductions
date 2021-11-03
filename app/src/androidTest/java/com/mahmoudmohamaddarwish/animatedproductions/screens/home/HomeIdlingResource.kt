package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import androidx.compose.ui.test.IdlingResource

object HomeIdlingResource : IdlingResource {
    override val isIdleNow: Boolean
        get() = MainActivityLoadingState.completedLoading
}
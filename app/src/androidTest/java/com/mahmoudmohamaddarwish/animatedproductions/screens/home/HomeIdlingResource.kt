package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import androidx.compose.ui.test.IdlingResource

/**
 * Due to a problem with the IdlingResource Compose library, I was able to implement the
 * IdlingResource interface only under the androidTest folder
 */
object HomeIdlingResource : IdlingResource {
    override val isIdleNow: Boolean
        get() = MainActivityLoadingState.completedLoading
}
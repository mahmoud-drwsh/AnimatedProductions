package com.mahmoudmohamaddarwish.animatedproductions.screens.moviedetails

object DetailsActivityIdlingResource {
    fun setIdleState(isIdle: Boolean) {
        this.isIdle = isIdle
    }

    var isIdle: Boolean = false
        private set
}
package com.mahmoudmohamaddarwish.animatedproductions.screens.home

object MainActivityIdlingResource {
    fun setIdleState(isIdle: Boolean) {
        this.isIdle = isIdle
    }

    var isIdle: Boolean = false
        private set
}
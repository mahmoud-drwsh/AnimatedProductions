package com.mahmoudmohamaddarwish.animatedproductions

import android.app.Activity

/**
 *
 * Although a separate method is not needed, I wrote this one to make it easier
 * to understand what's going on.
 * Navigating back works by letting the backstack decide what to display after this
 * activity finishes.
 * */
fun Activity.navigateUp() = finish()

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    class Error(val message: String) : Resource<Nothing>()
    class Success<T>(val data: T) : Resource<T>()
}
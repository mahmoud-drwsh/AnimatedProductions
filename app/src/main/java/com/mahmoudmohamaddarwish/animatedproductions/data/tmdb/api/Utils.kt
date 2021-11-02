package com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api


fun getPosterImageUrl(resourceName: String): String = "${Constants.BASE_URL_POSTERS}$resourceName"

fun getBackdropImageUrl(resourceName: String): String =
    "${Constants.BASE_URL_BACKDROPS}$resourceName"

package com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api

import com.mahmoudmohamaddarwish.animatedproductions.BuildConfig

object Constants {
    private const val IMAGES_BASE_URL = "https://image.tmdb.org/t/p/"
    const val BASE_URL_POSTERS: String = "${IMAGES_BASE_URL}w500/"
    const val BASE_URL_BACKDROPS: String = "${IMAGES_BASE_URL}w780/"
    const val BASE_URL_API: String = "https://api.themoviedb.org/3/"
    const val API_KEY: String = BuildConfig.API_KEY
}
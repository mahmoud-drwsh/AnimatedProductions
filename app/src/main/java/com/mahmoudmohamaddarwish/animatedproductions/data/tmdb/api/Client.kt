package com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api

import okhttp3.OkHttpClient

/**
 * This custom client is used in sort to pass an authentication interceptor to Retrofit
 * */
val okHttpClient: OkHttpClient =
    OkHttpClient
        .Builder()
        .addInterceptor(AuthenticationAPIKeyAddingInterceptor)
        .build()


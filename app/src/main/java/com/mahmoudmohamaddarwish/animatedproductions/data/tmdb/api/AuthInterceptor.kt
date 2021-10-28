package com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api

import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.Constants.API_KEY
import okhttp3.Interceptor

/**
 * This interceptor is used to add the authentication key for every request
 * */
internal val AuthenticationAPIKeyAddingInterceptor = Interceptor { chain ->
    val newHttpUrl = chain
        .request()
        .url()
        .newBuilder()
        .addQueryParameter("api_key", API_KEY)
        .build()

    val request = chain
        .request()
        .newBuilder()
        .url(newHttpUrl)
        .build()

    chain.proceed(request)
}
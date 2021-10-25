package com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api

import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.Constants.API_KEY
import okhttp3.Interceptor

/**
 * This interceptor is used to add the authentication header for every request
 * */
internal val AuthenticationHeaderAddingInterceptor = Interceptor { chain ->
    val httpUrl = chain
        .request()
        .url()
        .newBuilder()
        .addQueryParameter("api_key", API_KEY)
        .build()

    println(httpUrl.uri().toString())

    val request = chain
        .request()
        .newBuilder()
        .url(httpUrl)
        .build()

    chain.proceed(request)
}
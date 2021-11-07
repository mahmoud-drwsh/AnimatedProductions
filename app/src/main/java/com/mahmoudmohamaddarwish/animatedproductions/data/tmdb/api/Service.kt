package com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api

import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.model.DiscoverMoviesResponse
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.model.DiscoverTVResponse
import retrofit2.http.GET

interface Service {
    @GET("discover/movie?language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_watch_monetization_types=flatrate&certification.lte=PG&certification_country=US&with_genres=16&with_original_language=en&with_companies=521")
    suspend fun getMovies(): DiscoverMoviesResponse

    @GET("discover/tv?language=en-US&sort_by=popularity.desc&page=1&timezone=America%2FNew_York&include_null_first_air_dates=false&with_watch_monetization_types=flatrate&certification.lte=TV-PG&certification_country=US&with_genres=16&with_original_language=en")
    suspend fun getShows(): DiscoverTVResponse
}
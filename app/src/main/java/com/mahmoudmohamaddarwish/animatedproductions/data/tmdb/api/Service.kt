package com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api

import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.model.DiscoverMoviesResponse
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.model.DiscoverTVResponse
import retrofit2.http.GET

interface Service {
    @GET("discover/movie?language=en-US&sort_by=popularity.desc&certification_country=US&certification=G&include_adult=false&include_video=false&page=1&with_companies=3")
    suspend fun getMovies(): DiscoverMoviesResponse

    @GET("discover/tv?language=en-US&region=US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&certification_country=US&certification=PG&with_companies=1")
    suspend fun getShows(): DiscoverTVResponse
}
package com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api

import com.mahmoudmohamaddarwish.animatedproductions.domain.model.DiscoverMoviesResponse
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.DiscoverTVResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface Service {
    @GET("discover/movie?language=en-US&sort_by=popularity.desc&certification_country=US&certification=G&include_adult=false&include_video=false&page=1&with_companies=3")
    suspend fun getMovies(): DiscoverMoviesResponse

    @GET("discover/tv?language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&certification_country=US&certification=G&with_genres=16")
    suspend fun getShows(): DiscoverTVResponse
}
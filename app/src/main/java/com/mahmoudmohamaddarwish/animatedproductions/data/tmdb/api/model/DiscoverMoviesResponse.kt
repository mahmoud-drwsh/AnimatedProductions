package com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.model


import com.google.gson.annotations.SerializedName

data class DiscoverMoviesResponse(
    @SerializedName("results")
    val discoverMovieItemDtos: List<DiscoverMovieItemDto> = listOf(),
)
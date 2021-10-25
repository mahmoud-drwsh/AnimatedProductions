package com.mahmoudmohamaddarwish.animatedproductions.domain.model


import com.google.gson.annotations.SerializedName

data class DiscoverMoviesResponse(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val discoverMovieItems: List<DiscoverMovieItem> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
)
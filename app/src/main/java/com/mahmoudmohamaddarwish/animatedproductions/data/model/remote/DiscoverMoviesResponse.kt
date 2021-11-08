package com.mahmoudmohamaddarwish.animatedproductions.data.model.remote


import com.google.gson.annotations.SerializedName

data class DiscoverMoviesResponse(
    @SerializedName("results")
    val discoverMovieItemDtos: List<DiscoverMovieItemDto> = listOf(),
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0,
)
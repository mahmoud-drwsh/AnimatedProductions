package com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.model


import com.google.gson.annotations.SerializedName

data class DiscoverTVResponse(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val discoverTVItemDtos: List<DiscoverTVItemDto> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
)
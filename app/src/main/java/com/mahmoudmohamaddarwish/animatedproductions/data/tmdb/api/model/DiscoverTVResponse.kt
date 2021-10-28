package com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.model


import com.google.gson.annotations.SerializedName

data class DiscoverTVResponse(
    @SerializedName("results")
    val discoverTVItemDtos: List<DiscoverTVItemDto> = listOf(),
)
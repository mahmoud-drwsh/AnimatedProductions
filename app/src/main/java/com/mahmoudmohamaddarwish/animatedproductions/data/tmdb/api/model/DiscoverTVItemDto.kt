package com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.model


import com.google.gson.annotations.SerializedName
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.getBackdropImageUrl
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.getPosterImageUrl
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production

data class DiscoverTVItemDto(
    @SerializedName("backdrop_path")
    val backdropPath: String = "",
    @SerializedName("first_air_date")
    val firstAirDate: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("original_language")
    val originalLanguage: String = "",
    @SerializedName("original_name")
    val originalName: String = "",
    @SerializedName("overview")
    val overview: String = "",
    @SerializedName("popularity")
    val popularity: Double = 0.0,
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,
    @SerializedName("vote_count")
    val voteCount: Int = 0,
) {
    companion object {
        fun DiscoverTVItemDto.toProduction(): Production = Production(
            backdropPath = getBackdropImageUrl(backdropPath),
            firstAirDate = firstAirDate,
            id = id,
            name = name,
            originalLanguage = originalLanguage,
            overview = overview,
            popularity = popularity,
            posterPath = getPosterImageUrl(posterPath),
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }
}
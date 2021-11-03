package com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.model


import com.google.gson.annotations.SerializedName
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.getBackdropImageUrl
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.getPosterImageUrl
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production

data class DiscoverMovieItemDto(
    @SerializedName("backdrop_path")
    val backdropPath: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("original_language")
    val originalLanguage: String = "",
    @SerializedName("overview")
    val overview: String = "",
    @SerializedName("popularity")
    val popularity: Double = 0.0,
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("release_date")
    val releaseDate: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,
    @SerializedName("vote_count")
    val voteCount: Int = 0,
) {
    companion object {
        fun DiscoverMovieItemDto.toProduction(): Production = Production(
            id = id,
            type = Production.ProductionType.MOVIE,
            backdropPath = getBackdropImageUrl(backdropPath),
            firstAirDate = releaseDate,
            name = title,
            originalLanguage = originalLanguage,
            overview = overview,
            popularity = popularity,
            posterPath = getPosterImageUrl(posterPath),
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }
}
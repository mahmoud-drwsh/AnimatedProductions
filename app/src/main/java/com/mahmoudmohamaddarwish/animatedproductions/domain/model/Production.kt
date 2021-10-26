package com.mahmoudmohamaddarwish.animatedproductions.domain.model

data class Production(
    val backdropPath: String = "",
    val firstAirDate: String = "",
    val id: Int = 0,
    val name: String = "",
    val originalLanguage: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String = "",
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
)
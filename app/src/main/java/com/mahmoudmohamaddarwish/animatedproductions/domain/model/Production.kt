package com.mahmoudmohamaddarwish.animatedproductions.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable {
    companion object {
        val dummy = Production(
            backdropPath = "https://image.tmdb.org/t/p/w780//hwwFyowfcbLRVmRBOkvnABBNIOs.jpg",
            firstAirDate = "1998-11-25",
            id = 9487,
            name = """A Bug's Life""",
            originalLanguage = "en",
            overview = """On behalf of "oppressed bugs everywhere," an inventive ant named Flik hires a troupe of warrior bugs to defend his bustling colony from a horde of freeloading grasshoppers led by the evil-minded Hopper.""",
            popularity = 67.211,
            posterPath = "https://image.tmdb.org/t/p/w500//hFamOus53922agTlKxhcL7ngJ9h.jpg",
            voteAverage = 7.0,
            voteCount = 7252
        )
    }
}
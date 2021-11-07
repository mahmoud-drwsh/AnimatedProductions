package com.mahmoudmohamaddarwish.animatedproductions.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.mahmoudmohamaddarwish.animatedproductions.data.room.Constants.ID_COL_NAME
import kotlinx.parcelize.Parcelize

@Entity(primaryKeys = [ID_COL_NAME], tableName = "favoriteProductions")
@Parcelize
data class Production(
    @ColumnInfo(name = ID_COL_NAME) val id: Int = 0,
    val type: ProductionType,
    val backdropPath: String = "",
    val firstAirDate: String = "",
    val name: String = "",
    val originalLanguage: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String = "",
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
) : Parcelable {
    enum class ProductionType { SHOW, MOVIE }

    companion object {
        val movieDummy = Production(
            id = 9487,
            type = ProductionType.MOVIE,
            backdropPath = "https://image.tmdb.org/t/p/w780//hwwFyowfcbLRVmRBOkvnABBNIOs.jpg",
            firstAirDate = "1998-11-25",
            name = """A Bug's Life""",
            originalLanguage = "en",
            overview = """On behalf of "oppressed bugs everywhere," an inventive ant named Flik hires a troupe of warrior bugs to defend his bustling colony from a horde of freeloading grasshoppers led by the evil-minded Hopper.""",
            popularity = 67.211,
            posterPath = "https://image.tmdb.org/t/p/w500//hFamOus53922agTlKxhcL7ngJ9h.jpg",
            voteAverage = 7.0,
            voteCount = 7252
        )

        val showDummy = Production(
            id = 82856,
            type = ProductionType.SHOW,
            backdropPath = "https://image.tmdb.org/t/p/w780//9ijMGlJKqcslswWUzTEwScm82Gs.jpg",
            firstAirDate = "2019-11-12",
            name = "The Mandalorian",
            originalLanguage = "en",
            overview = """After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.""",
            popularity = 211.145,
            posterPath = "https://image.tmdb.org/t/p/w500//sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg",
            voteAverage = 8.5,
            voteCount = 6930,
        )
    }
}
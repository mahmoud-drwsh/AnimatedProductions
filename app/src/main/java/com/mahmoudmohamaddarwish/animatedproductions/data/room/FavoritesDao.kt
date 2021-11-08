package com.mahmoudmohamaddarwish.animatedproductions.data.room

import androidx.room.*
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(production: Production)

    @Delete
    suspend fun deleteFavorite(production: Production)

    @Query("SELECT * FROM favoriteProductions WHERE type = :type")
    fun getFavoriteMovies(type: Production.ProductionType = Production.ProductionType.MOVIE): Flow<List<Production>>

    @Query("SELECT * FROM favoriteProductions WHERE type = :type")
    fun getFavoriteShows(type: Production.ProductionType = Production.ProductionType.SHOW): Flow<List<Production>>

    @Query("DELETE FROM favoriteProductions")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM favoriteProductions WHERE _id = :id)")
    fun isProductionAFavorite(id: Int): Flow<Boolean>
}


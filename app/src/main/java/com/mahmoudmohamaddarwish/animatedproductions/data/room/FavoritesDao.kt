package com.mahmoudmohamaddarwish.animatedproductions.data.room

import androidx.room.*
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(production: Production)

    @Delete
    suspend fun deleteFavorite(production: Production)

    @Query("SELECT * FROM Production")
    fun getFavorites(): Flow<List<Production>>
}


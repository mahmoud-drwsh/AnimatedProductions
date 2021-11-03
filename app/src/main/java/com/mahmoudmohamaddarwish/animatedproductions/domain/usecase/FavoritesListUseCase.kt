package com.mahmoudmohamaddarwish.animatedproductions.domain.usecase

import androidx.room.Dao
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
import kotlinx.coroutines.flow.Flow

interface FavoritesListUseCase {
    val favoritesFlow: Flow<Resource<List<Production>>>

    suspend fun addFavorite(production: Production)
    suspend fun removeFavorite(production: Production)
}
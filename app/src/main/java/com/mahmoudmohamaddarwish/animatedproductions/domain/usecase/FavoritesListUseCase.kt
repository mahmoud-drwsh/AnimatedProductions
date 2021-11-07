package com.mahmoudmohamaddarwish.animatedproductions.domain.usecase

import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
import kotlinx.coroutines.flow.Flow

interface FavoritesListUseCase {

    val favoriteMoviesFlow: Flow<Resource<List<Production>>>

    val favoriteShowsFlow: Flow<Resource<List<Production>>>


    fun deleteAll()

    suspend fun addFavorite(production: Production)

    suspend fun removeFavorite(production: Production)

    fun isProductionAFavorite(id: Int): Flow<Boolean>
}
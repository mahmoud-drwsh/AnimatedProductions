package com.mahmoudmohamaddarwish.animatedproductions.domain.usecase

import androidx.paging.PagingSource
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import kotlinx.coroutines.flow.Flow

interface FavoritesListUseCase {

    fun favoriteMoviesPagingSource(): PagingSource<Int, Production>

    fun favoriteShowsPagingSource(): PagingSource<Int, Production>

    fun deleteAll()

    suspend fun addFavorite(production: Production)

    suspend fun removeFavorite(production: Production)

    fun isAFavorite(id: Int): Flow<Boolean>
}
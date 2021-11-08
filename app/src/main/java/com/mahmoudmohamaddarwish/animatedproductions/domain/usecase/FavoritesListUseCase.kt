package com.mahmoudmohamaddarwish.animatedproductions.domain.usecase

import androidx.paging.PagingSource
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import kotlinx.coroutines.flow.Flow

interface FavoritesListUseCase {

    val favoriteMoviesFlow: Flow<Resource<List<Production>>>

    val favoriteShowsFlow: Flow<Resource<List<Production>>>

    val favoriteMoviesPagingSource: PagingSource<Int, Production>

    val favoriteShowsPagingSource: PagingSource<Int, Production>

    fun deleteAll()

    suspend fun addFavorite(production: Production)

    suspend fun removeFavorite(production: Production)

    fun isProductionAFavorite(id: Int): Flow<Boolean>
}
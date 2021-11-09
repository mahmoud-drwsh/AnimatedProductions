package com.mahmoudmohamaddarwish.animatedproductions.data.repos

import androidx.paging.PagingSource
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import com.mahmoudmohamaddarwish.animatedproductions.data.room.FavoritesDao
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.FavoritesListUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class FavoritesListRepo @Inject constructor(
    private val dao: FavoritesDao,
) : FavoritesListUseCase {

    override fun favoriteShowsPagingSource(): PagingSource<Int, Production> =
        dao.getPagedFavoriteShows()

    override fun favoriteMoviesPagingSource(): PagingSource<Int, Production> =
        dao.getPagedFavoriteMovies()

    override fun deleteAll() = dao.deleteAll()

    override suspend fun addFavorite(production: Production) = dao.insertFavorite(production)

    override suspend fun removeFavorite(production: Production) = dao.deleteFavorite(production)

    override fun isAFavorite(id: Int): Flow<Boolean> = dao.isProductionAFavorite(id)
}



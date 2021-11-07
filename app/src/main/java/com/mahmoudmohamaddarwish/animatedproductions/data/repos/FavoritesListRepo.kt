package com.mahmoudmohamaddarwish.animatedproductions.data.repos

import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.room.FavoritesDao
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.FavoritesListUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class FavoritesListRepo @Inject constructor(
    private val dao: FavoritesDao,
) : FavoritesListUseCase {

    override val favoriteMoviesFlow: Flow<Resource<List<Production>>> =
        channelFlow {
            send(Resource.Loading)
            dao.getFavoriteMovies().collect {
                send(Resource.Success(it))
            }.runCatching {
                send(Resource.Error("Unknown error"))
            }
        }


    override val favoriteShowsFlow: Flow<Resource<List<Production>>> =
        channelFlow {
            send(Resource.Loading)
            dao.getFavoriteShows().collect {
                send(Resource.Success(it))
            }.runCatching {
                send(Resource.Error("Unknown error"))
            }
        }

    override fun deleteAll() {
        dao.deleteAll()
    }

    override suspend fun addFavorite(production: Production) = dao.insertFavorite(production)

    override suspend fun removeFavorite(production: Production) = dao.deleteFavorite(production)
}

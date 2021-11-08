package com.mahmoudmohamaddarwish.animatedproductions.data.repos

import android.content.res.Resources
import com.mahmoudmohamaddarwish.animatedproductions.R
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.room.FavoritesDao
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Order
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.FavoritesListUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class OrderedFavoritesListRepo @Inject constructor(
    private val dao: FavoritesDao,
    private val orderRepo: OrderRepo,
    private val resources: Resources,
) : FavoritesListUseCase {

    private fun Flow<Resource<List<Production>>>.sorted() =
        combine(orderRepo.order) { a: Resource<List<Production>>, b: Order ->
            sortProductionsContainedInSuccessState(order = b, resource = a)
        }

    override val favoriteMoviesFlow: Flow<Resource<List<Production>>> =
        channelFlow {
            send(Resource.Loading)
            dao.getFavoriteMovies().collect {
                send(Resource.Success(it))
            }.runCatching {
                send(Resource.Error(resources.getString(R.string.unexpected_error_message)))
            }
        }.sorted()


    override val favoriteShowsFlow: Flow<Resource<List<Production>>> =
        channelFlow {
            send(Resource.Loading)
            dao.getFavoriteShows().collect {
                send(Resource.Success(it))
            }.runCatching {
                send(Resource.Error(resources.getString(R.string.unexpected_error_message)))
            }
        }.sorted()

    override fun deleteAll() {
        dao.deleteAll()
    }

    override suspend fun addFavorite(production: Production) = dao.insertFavorite(production)

    override suspend fun removeFavorite(production: Production) = dao.deleteFavorite(production)

    override fun isProductionAFavorite(id: Int): Flow<Boolean> = dao.isProductionAFavorite(id)
}

package com.mahmoudmohamaddarwish.animatedproductions.data.repos

import android.content.res.Resources
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mahmoudmohamaddarwish.animatedproductions.R
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Order
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import com.mahmoudmohamaddarwish.animatedproductions.data.model.remote.DiscoverMovieItemDto.Companion.toProduction
import com.mahmoudmohamaddarwish.animatedproductions.data.model.remote.DiscoverTVItemDto.Companion.toProduction
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.Service
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.getMoviesSortingQueryArgument
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.getShowsSortingQueryArgument
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.ListMoviesAndShowsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListMoviesAndShowsRepo @Inject constructor(
    private val service: Service,
    private val resources: Resources,
) : ListMoviesAndShowsUseCase {

    override val moviesFlow: Flow<Resource<List<Production>>> = flow {
        emit(Resource.Loading)


        val data = service.getMovies()
            .discoverMovieItemDtos
            .map { it.toProduction() }
            .removeProductionsWithoutImages()

        emit(Resource.Success(data))
    }.catch { e ->
        emit(Resource.Error(e.message
            ?: resources.getString(R.string.unexpected_error_message)))
    }

    @Suppress("UselessCallOnNotNull")
    override val showsFlow: Flow<Resource<List<Production>>> = flow {
        emit(Resource.Loading)

        val shows = service.getShows().discoverTVItemDtos
            .filterNot { it.name.contains("family guy", ignoreCase = true) }
            .map { it.toProduction() }
            .removeProductionsWithoutImages()

        emit(Resource.Success(shows))
    }.catch { e ->
        emit(Resource.Error(e.message
            ?: resources.getString(R.string.unexpected_error_message)))
    }


}

class ShowsDataSource(
    private val service: Service,
    private val order: Order,
) : PagingSource<Int, Production>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Production> {
        val page = params.key ?: 1

        return try {

            val data = service.getShows(page, getShowsSortingQueryArgument(order))

            val productions: List<Production> = data.discoverTVItemDtos
                .filterNot(::profaneShowsSelector)
                .map { it.toProduction() }
                .removeProductionsWithoutImages()

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (page >= data.totalPages) null else page + 1

            LoadResult.Page(
                data = productions,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(IllegalStateException())
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Production>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}


class MoviesDataSource(
    private val service: Service,
    private val order: Order,
) : PagingSource<Int, Production>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Production> {
        val page = params.key ?: 1

        return try {

            val data = service.getMovies(page, getMoviesSortingQueryArgument(order))

            val productions: List<Production> = data.discoverMovieItemDtos
                .map { it.toProduction() }
                .removeProductionsWithoutImages()

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (page >= data.totalPages) null else page + 1

            LoadResult.Page(
                data = productions,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Production>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
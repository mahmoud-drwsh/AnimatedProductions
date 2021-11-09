package com.mahmoudmohamaddarwish.animatedproductions.data.repos

import android.content.res.Resources
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import com.mahmoudmohamaddarwish.animatedproductions.data.model.remote.DiscoverMovieItemDto.Companion.toProduction
import com.mahmoudmohamaddarwish.animatedproductions.data.model.remote.DiscoverTVItemDto.Companion.toProduction
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.Service
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.ListMoviesAndShowsUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListMoviesAndShowsRepo @Inject constructor(
    private val service: Service,
    private val resources: Resources,
) : ListMoviesAndShowsUseCase {

    override fun moviesPagingSource(): PagingSource<Int, Production> = MoviesDataSource(service)

    override fun showsPagingSource(): PagingSource<Int, Production> = ShowsDataSource(service)

    private class ShowsDataSource(
        private val service: Service,
    ) : PagingSource<Int, Production>() {

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Production> {
            val page = params.key ?: 1

            return try {

                val data = service.getShows(page)

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

    private class MoviesDataSource(
        private val service: Service,
    ) : PagingSource<Int, Production>() {

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Production> {
            val page = params.key ?: 1

            return try {

                val data = service.getMovies(page)

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
}

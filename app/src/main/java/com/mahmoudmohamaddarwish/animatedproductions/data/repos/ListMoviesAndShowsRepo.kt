package com.mahmoudmohamaddarwish.animatedproductions.data.repos

import android.content.res.Resources
import android.util.Log
import com.mahmoudmohamaddarwish.animatedproductions.R
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.Service
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.model.DiscoverMovieItemDto.Companion.toProduction
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.model.DiscoverTVItemDto
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.model.DiscoverTVItemDto.Companion.toProduction
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.ListMoviesAndShowsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListMoviesAndShowsRepo @Inject constructor(
    private val service: Service,
    private val resources: Resources,
) : ListMoviesAndShowsUseCase {

    override val moviesFlow: Flow<Resource<List<Production>>> = flow {
        emit(Resource.Loading)

        val data = service.getMoviesDreamWorks().items.map { it.toProduction() }

        emit(Resource.Success(data))
    }.catch { e ->
        emit(Resource.Error(e.message
            ?: resources.getString(R.string.unexpected_error_message)))
    }

    @Suppress("UselessCallOnNotNull")
    override val showsFlow: Flow<Resource<List<Production>>> = flow {
        emit(Resource.Loading)

        val removeProductionsWithoutImages: (DiscoverTVItemDto) -> Boolean = {
            it.posterPath.isNullOrBlank().not() && it.backdropPath.isNullOrBlank().not()
        }

        val shows = service.getShows().discoverTVItemDtos
            .filter(removeProductionsWithoutImages)
            .map { it.toProduction() }

        emit(Resource.Success(shows))
    }.catch { e ->
        emit(Resource.Error(e.message
            ?: resources.getString(R.string.unexpected_error_message)))
    }.onEach {
        if (it is Resource.Success) {
            Log.e(TAG, "${it.data}: ")
        }
    }
}

private const val TAG = "ListMoviesAndShowsRepo"
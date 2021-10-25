package com.mahmoudmohamaddarwish.animatedproductions.data.repos

import android.content.res.Resources
import com.mahmoudmohamaddarwish.animatedproductions.R
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.Service
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.DiscoverMoviesResponse
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.DiscoverTVResponse
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

    override val moviesFlow: Flow<Resource<DiscoverMoviesResponse>> = flow {
        emit(Resource.Loading)

        emit(Resource.Success(service.getMovies()))
    }.catch { e ->
        emit(Resource.Error(e.message
            ?: resources.getString(R.string.unexpected_error_message)))
    }

    override val showsFlow: Flow<Resource<DiscoverTVResponse>> = flow {
        emit(Resource.Loading)

        val shows = service.getShows()

        emit(Resource.Success(shows.copy(
            discoverTVItems = shows.discoverTVItems.filter { it.posterPath != null }
        )))
    }.catch { e ->
        emit(Resource.Error(e.message
            ?: resources.getString(R.string.unexpected_error_message)))
    }
}

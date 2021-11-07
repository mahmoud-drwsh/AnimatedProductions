package com.mahmoudmohamaddarwish.animatedproductions.data.repos

import android.content.res.Resources
import com.mahmoudmohamaddarwish.animatedproductions.R
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.Service
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.model.DiscoverMovieItemDto
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.model.DiscoverMovieItemDto.Companion.toProduction
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.model.DiscoverTVItemDto
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.model.DiscoverTVItemDto.Companion.toProduction
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
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

    @Suppress("UselessCallOnNotNull")
    override val moviesFlow: Flow<Resource<List<Production>>> = flow {
        emit(Resource.Loading)

        val removeProductionsWithoutImages: (DiscoverMovieItemDto) -> Boolean = {
            it.posterPath.isNullOrBlank().not() && it.backdropPath.isNullOrBlank().not()
        }

        val data = service.getMovies().discoverMovieItemDtos.filter(removeProductionsWithoutImages)
            .map { it.toProduction() }

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
            .filterNot { it.name.contains("family guy", ignoreCase = true) }
            .map { it.toProduction() }

        emit(Resource.Success(shows))
    }.catch { e ->
        emit(Resource.Error(e.message
            ?: resources.getString(R.string.unexpected_error_message)))
    }
}
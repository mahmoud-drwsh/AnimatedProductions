package com.mahmoudmohamaddarwish.animatedproductions.domain.usecase

import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.DiscoverMoviesResponse
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.DiscoverTVResponse
import kotlinx.coroutines.flow.Flow

interface ListMoviesAndShowsUseCase {
    val moviesFlow: Flow<Resource<DiscoverMoviesResponse>>
    val showsFlow: Flow<Resource<DiscoverTVResponse>>
}


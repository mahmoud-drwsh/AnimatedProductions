package com.mahmoudmohamaddarwish.animatedproductions.domain.usecase

import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import kotlinx.coroutines.flow.Flow

interface ListMoviesAndShowsUseCase {
    val moviesFlow: Flow<Resource<List<Production>>>
    val showsFlow: Flow<Resource<List<Production>>>
}


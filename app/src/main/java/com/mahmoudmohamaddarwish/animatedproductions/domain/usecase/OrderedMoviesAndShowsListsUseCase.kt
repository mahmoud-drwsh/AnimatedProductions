package com.mahmoudmohamaddarwish.animatedproductions.domain.usecase

import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import kotlinx.coroutines.flow.Flow

interface OrderedMoviesAndShowsListsUseCase {
    val orderedMoviesFlow: Flow<Resource<List<Production>>>
    val orderedShowsFlow: Flow<Resource<List<Production>>>
}
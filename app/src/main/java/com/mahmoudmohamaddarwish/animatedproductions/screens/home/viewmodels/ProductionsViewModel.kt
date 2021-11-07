package com.mahmoudmohamaddarwish.animatedproductions.screens.home.viewmodels

import androidx.lifecycle.ViewModel
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.OrderedMoviesAndShowsListsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProductionsViewModel @Inject constructor(
    orderedMoviesAndShowsListsUseCase: OrderedMoviesAndShowsListsUseCase,
) : ViewModel() {

    val orderedMoviesFlow: Flow<Resource<List<Production>>> =
        orderedMoviesAndShowsListsUseCase.orderedMoviesFlow

    val orderedShowsFlow: Flow<Resource<List<Production>>> =
        orderedMoviesAndShowsListsUseCase.orderedShowsFlow
}
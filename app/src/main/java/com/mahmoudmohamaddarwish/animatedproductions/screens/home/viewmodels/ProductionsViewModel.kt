package com.mahmoudmohamaddarwish.animatedproductions.screens.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import com.mahmoudmohamaddarwish.animatedproductions.data.repos.ShowsDataSource
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.Service
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.OrderedMoviesAndShowsListsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProductionsViewModel @Inject constructor(
    orderedMoviesAndShowsListsUseCase: OrderedMoviesAndShowsListsUseCase,
    val service: Service,
) : ViewModel() {

    val orderedMoviesFlow: Flow<Resource<List<Production>>> =
        orderedMoviesAndShowsListsUseCase.orderedMoviesFlow

    val orderedShowsFlow: Flow<Resource<List<Production>>> =
        orderedMoviesAndShowsListsUseCase.orderedShowsFlow

    val f: Flow<PagingData<Production>> = Pager(PagingConfig(20, 20, false, 20)) {
        ShowsDataSource(service)
    }.flow
}


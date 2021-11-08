package com.mahmoudmohamaddarwish.animatedproductions.screens.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import com.mahmoudmohamaddarwish.animatedproductions.data.repos.MoviesDataSource
import com.mahmoudmohamaddarwish.animatedproductions.data.repos.OrderRepo
import com.mahmoudmohamaddarwish.animatedproductions.data.repos.ShowsDataSource
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.Service
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.OrderedMoviesAndShowsListsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class ProductionsViewModel @Inject constructor(
    orderedMoviesAndShowsListsUseCase: OrderedMoviesAndShowsListsUseCase,
    private val service: Service,
    private val orderRepo: OrderRepo,
) : ViewModel() {



    val orderedMoviesFlow: Flow<Resource<List<Production>>> =
        orderedMoviesAndShowsListsUseCase.orderedMoviesFlow

    val orderedShowsFlow: Flow<Resource<List<Production>>> =
        orderedMoviesAndShowsListsUseCase.orderedShowsFlow

    val showsDataSourceFlow: Flow<PagingData<Production>> =
        orderRepo.order.flatMapLatest { order ->
            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = false
                )
            ) {
                ShowsDataSource(service, order)
            }.flow
        }.cachedIn(viewModelScope)


    val moviesDataSourceFlow: Flow<PagingData<Production>> =
        orderRepo.order.flatMapLatest { order ->
            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = false
                )
            ) {
                MoviesDataSource(service, order)
            }.flow
        }.cachedIn(viewModelScope)
}


package com.mahmoudmohamaddarwish.animatedproductions.screens.shared_viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.Service
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.ListMoviesAndShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class ProductionsViewModel @Inject constructor(
    private val service: Service,
    listMoviesAndShowsUseCase: ListMoviesAndShowsUseCase,
) : ViewModel() {

    val showsDataSourceFlow: Flow<PagingData<Production>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        )
    ) {
        listMoviesAndShowsUseCase.showsPagingSource()
    }.flow

    val moviesDataSourceFlow: Flow<PagingData<Production>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        )
    ) {
        listMoviesAndShowsUseCase.moviesPagingSource()
    }.flow
}


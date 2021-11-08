package com.mahmoudmohamaddarwish.animatedproductions.screens.shared_viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import com.mahmoudmohamaddarwish.animatedproductions.di.CoroutinesScopesModule.ApplicationScope
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.FavoritesListUseCase
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.OrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesListUseCase: FavoritesListUseCase,
    private val orderUseCase: OrderUseCase,
    @ApplicationScope private val coroutineScope: CoroutineScope,
) : ViewModel() {
    val movies: Flow<Resource<List<Production>>> = favoritesListUseCase.favoriteMoviesFlow
    val shows: Flow<Resource<List<Production>>> = favoritesListUseCase.favoriteShowsFlow

    val moviesPaged: Flow<PagingData<Production>> =
        Pager(PagingConfig(20)) { favoritesListUseCase.favoriteMoviesPagingSource }.flow

    val showsPaged = Pager(PagingConfig(20)) { favoritesListUseCase.favoriteShowsPagingSource }.flow


    private fun addFavorite(production: Production) = coroutineScope.launch {
        favoritesListUseCase.addFavorite(production)
    }

    private fun removeFavorite(production: Production) = coroutineScope.launch {
        favoritesListUseCase.removeFavorite(production)
    }

    fun toggleFavoriteStatus(production: Production, isProductionAFavorite: Boolean) =
        coroutineScope.launch {
            if (isProductionAFavorite) removeFavorite(production) else addFavorite(production)
        }

    fun isProductionAFavorite(id: Int): Flow<Boolean> =
        favoritesListUseCase.isProductionAFavorite(id)
}
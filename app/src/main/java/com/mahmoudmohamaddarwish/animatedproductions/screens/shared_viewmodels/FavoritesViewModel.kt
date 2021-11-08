package com.mahmoudmohamaddarwish.animatedproductions.screens.shared_viewmodels

import androidx.lifecycle.ViewModel
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.di.CoroutinesScopesModule.ApplicationScope
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.FavoritesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesListUseCase: FavoritesListUseCase,
    @ApplicationScope val coroutineScope: CoroutineScope,
) : ViewModel() {
    val movies: Flow<Resource<List<Production>>> = favoritesListUseCase.favoriteMoviesFlow
    val shows: Flow<Resource<List<Production>>> = favoritesListUseCase.favoriteShowsFlow

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
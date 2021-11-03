package com.mahmoudmohamaddarwish.animatedproductions.domain.usecase

import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production

interface ManagingFavoritesListUseCase {
    suspend fun addFavorite(production: Production)
    suspend fun removeFavorite(production: Production)
}



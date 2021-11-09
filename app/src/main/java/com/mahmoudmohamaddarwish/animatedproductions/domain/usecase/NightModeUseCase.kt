package com.mahmoudmohamaddarwish.animatedproductions.domain.usecase

import kotlinx.coroutines.flow.Flow

interface NightModeUseCase {
    val isNightModeEnabled: Flow<Boolean>

    fun toggleNightMode()
}


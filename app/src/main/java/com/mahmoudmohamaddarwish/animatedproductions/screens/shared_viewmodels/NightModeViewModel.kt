package com.mahmoudmohamaddarwish.animatedproductions.screens.shared_viewmodels

import androidx.lifecycle.ViewModel
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.NightModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NightModeViewModel @Inject constructor(
    private val nightModeUseCase: NightModeUseCase,
) : ViewModel() {
    val isNightModeEnabled = nightModeUseCase.isNightModeEnabled
    fun toggleNightMode() = nightModeUseCase.toggleNightMode()
}
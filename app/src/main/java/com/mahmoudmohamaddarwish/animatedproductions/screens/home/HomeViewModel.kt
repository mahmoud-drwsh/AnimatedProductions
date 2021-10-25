package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import androidx.lifecycle.ViewModel
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.OrderingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    orderingUseCase: OrderingUseCase,
) : ViewModel() {

}
package com.mahmoudmohamaddarwish.animatedproductions.screens.home.viewmodels

import androidx.lifecycle.ViewModel
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Order
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.OrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProductionsOrderViewModel @Inject constructor(
    private val orderUseCase: OrderUseCase,
) : ViewModel() {
    val order: Flow<Order> = orderUseCase.order

    fun setSortProperty(propertyName: Order.Property) =
        orderUseCase.setSortProperty(propertyName)

    fun setSortType(type: Order.Type) =
        orderUseCase.setSortType(type)
}


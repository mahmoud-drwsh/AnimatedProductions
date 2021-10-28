package com.mahmoudmohamaddarwish.animatedproductions.domain.usecase

import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderUseCase {
    val order: Flow<Order>

    fun setSortProperty(propertyName: Order.Property)
    fun setSortType(type: Order.Type)
}
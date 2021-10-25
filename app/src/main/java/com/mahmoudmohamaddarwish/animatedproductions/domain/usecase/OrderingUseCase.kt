package com.mahmoudmohamaddarwish.animatedproductions.domain.usecase

import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderingUseCase {
    val order: Flow<Order>

    fun setOrderProperty(propertyName: Order.Property)
    fun setOrderType(type: Order.Type)
}
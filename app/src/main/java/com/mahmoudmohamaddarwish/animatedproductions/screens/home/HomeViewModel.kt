package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import androidx.lifecycle.ViewModel
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Order
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.OrderUseCase
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.OrderedMoviesAndShowsListsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val orderUseCase: OrderUseCase,
    orderedMoviesAndShowsListsUseCase: OrderedMoviesAndShowsListsUseCase,
) : ViewModel() {

    val orderedMoviesFlow: Flow<Resource<List<Production>>> =
        orderedMoviesAndShowsListsUseCase.orderedMoviesFlow

    val orderedShowsFlow: Flow<Resource<List<Production>>> =
        orderedMoviesAndShowsListsUseCase.orderedShowsFlow

    val order: Flow<Order> = orderUseCase.order

    fun setSortProperty(propertyName: Order.Property) =
        orderUseCase.setSortProperty(propertyName)

    fun setSortType(type: Order.Type) =
        orderUseCase.setSortType(type)

}
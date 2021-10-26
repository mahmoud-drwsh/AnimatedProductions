package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import androidx.lifecycle.ViewModel
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.model.DiscoverMovieItemDto
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.model.DiscoverMoviesResponse
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.model.DiscoverTVItemDto
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.model.DiscoverTVResponse
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.*
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.ListMoviesAndShowsUseCase
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.OrderingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    public val orderingUseCase: OrderingUseCase,
    moviesAndShowsUseCase: ListMoviesAndShowsUseCase,
) : ViewModel() {

    val moviesFlow =
        orderingUseCase
            .order
            .combine(moviesAndShowsUseCase.moviesFlow) { order, resource ->
                if (resource is Resource.Success) {
                    val newData =
                        resource.sortProductions(order)

                    Resource.Success(data = newData)
                } else {
                    resource
                }
            }


    val showsFlow =
        orderingUseCase
            .order
            .combine(moviesAndShowsUseCase.showsFlow) { order, resource ->
                if (resource is Resource.Success) {
                    val newData = resource.sortProductions(order)

                    Resource.Success(data = newData)
                } else {
                    resource
                }
            }

    private fun Resource.Success<List<Production>>.sortProductions(
        order: Order,
    ): List<Production> {
        return data.sortList(order) {
            when (order.property) {
                Order.Property.Name -> it.name
                Order.Property.RELEASE_DATE -> it.firstAirDate
            }
        }
    }

    private fun <T, R : Comparable<R>> List<T>.sortList(order: Order, selector: (T) -> R): List<T> {
        return when (order.type) {
            Order.Type.ASCENDING -> sortedBy(selector)
            Order.Type.DESCENDING -> sortedByDescending(selector)
        }
    }
}

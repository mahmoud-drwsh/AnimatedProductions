package com.mahmoudmohamaddarwish.animatedproductions.data.repos

import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Order
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.OrderedMoviesAndShowsListsUseCase
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class OrderedMoviesAndShowsRepo @Inject constructor(
    listMoviesAndShowsRepo: ListMoviesAndShowsRepo,
    orderRepo: OrderRepo,
) : OrderedMoviesAndShowsListsUseCase {

    override val orderedMoviesFlow =
        orderRepo
            .order
            .combine(listMoviesAndShowsRepo.moviesFlow) { order, resource ->
                if (resource is Resource.Success) {
                    val newData =
                        resource.sortProductions(order)

                    Resource.Success(data = newData)
                } else {
                    resource
                }
            }


    override val orderedShowsFlow =
        orderRepo
            .order
            .combine(listMoviesAndShowsRepo.showsFlow) { order, resource ->
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
package com.mahmoudmohamaddarwish.animatedproductions.data.repos

import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Order
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production


fun Resource.Success<List<Production>>.sortProductions(order: Order): List<Production> {
    val selector = { it: Production ->
        when (order.property) {
            Order.Property.Name -> it.name
            Order.Property.RELEASE_DATE -> it.firstAirDate
        }
    }

    return when (order.type) {
        Order.Type.ASCENDING -> data.sortedBy(selector)
        Order.Type.DESCENDING -> data.sortedByDescending(selector)
    }
}

fun sortProductionsContainedInSuccessState(
    order: Order,
    resource: Resource<List<Production>>,
): Resource<List<Production>> = if (resource is Resource.Success) {
    Resource.Success(data = resource.sortProductions(order))
} else {
    resource
}

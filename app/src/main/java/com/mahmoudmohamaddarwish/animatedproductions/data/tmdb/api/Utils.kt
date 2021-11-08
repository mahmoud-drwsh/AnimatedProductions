package com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api

import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Order


fun getPosterImageUrl(resourceName: String): String = "${Constants.BASE_URL_POSTERS}$resourceName"

fun getBackdropImageUrl(resourceName: String): String =
    "${Constants.BASE_URL_BACKDROPS}$resourceName"

fun getMoviesSortingQueryArgument(order: Order) = buildString {
    append(
        when (order.property) {
            Order.Property.VOTE_AVERAGE -> "vote_average"
            Order.Property.RELEASE_DATE -> "release_date"
            Order.Property.POPULARITY -> "popularity"
        }
    )
    append(".")
    append(
        when (order.type) {
            Order.Type.ASCENDING -> "asc"
            Order.Type.DESCENDING -> "desc"
        }
    )
}

fun getShowsSortingQueryArgument(order: Order) = buildString {
    append(
        when (order.property) {
            Order.Property.VOTE_AVERAGE -> "vote_average"
            Order.Property.RELEASE_DATE -> "first_air_date"
            Order.Property.POPULARITY -> "popularity"
        }
    )
    append(".")
    append(
        when (order.type) {
            Order.Type.ASCENDING -> "asc"
            Order.Type.DESCENDING -> "desc"
        }
    )
}

/*
vote_average.desc,
vote_average.asc,
first_air_date.desc,
first_air_date.asc,
popularity.desc,
popularity.asc
 */

/*
popularity.asc,
popularity.desc,
release_date.asc,
release_date.desc,
revenue.asc,
revenue.desc,
primary_release_date.asc,
primary_release_date.desc,
original_title.asc,
original_title.desc,
vote_average.asc,
vote_average.desc,
vote_count.asc,
vote_count.desc
* */
package com.mahmoudmohamaddarwish.animatedproductions.data.repos

import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import com.mahmoudmohamaddarwish.animatedproductions.data.model.remote.DiscoverTVItemDto

fun List<Production>.removeProductionsWithoutImages() = filterNot { production ->
    production.posterPath.isBlank() || production.backdropPath.isBlank()
}

fun profaneShowsSelector(discoverTVItemDto: DiscoverTVItemDto): Boolean =
    discoverTVItemDto.name.contains("family guy", ignoreCase = true)
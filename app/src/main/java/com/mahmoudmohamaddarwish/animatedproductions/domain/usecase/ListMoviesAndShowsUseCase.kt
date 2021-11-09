package com.mahmoudmohamaddarwish.animatedproductions.domain.usecase

import androidx.paging.PagingSource
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production

interface ListMoviesAndShowsUseCase {

    fun moviesPagingSource(): PagingSource<Int, Production>

    fun showsPagingSource(): PagingSource<Int, Production>
}


package com.mahmoudmohamaddarwish.animatedproductions.data.repos

import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.OrderedMoviesAndShowsListsUseCase
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class OrderedMoviesAndShowsRepo @Inject constructor(
    listMoviesAndShowsRepo: ListMoviesAndShowsRepo,
    orderRepo: OrderRepo,
) : OrderedMoviesAndShowsListsUseCase {
    override val orderedMoviesFlow =
        orderRepo.order.combine(listMoviesAndShowsRepo.moviesFlow,
            transform = ::sortProductionsContainedInSuccessState)

    override val orderedShowsFlow =
        orderRepo.order.combine(listMoviesAndShowsRepo.showsFlow,
            transform = ::sortProductionsContainedInSuccessState)
}


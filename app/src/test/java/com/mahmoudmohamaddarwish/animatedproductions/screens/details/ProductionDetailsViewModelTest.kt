package com.mahmoudmohamaddarwish.animatedproductions.screens.details

import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import com.mahmoudmohamaddarwish.animatedproductions.screens.shared_viewmodels.ProductionDetailsViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class ProductionDetailsViewModelTest {

    private lateinit var productionDetailsViewModel: ProductionDetailsViewModel

    @Before
    fun setUp() {
        productionDetailsViewModel = ProductionDetailsViewModel()
    }

    @Test
    fun productionObjectLoadsAfterBeingPassedToTheViewModel() = runBlocking {
        productionDetailsViewModel.loadProductionObject(Production.movieDummy)

        val firstSuccessStateResource = productionDetailsViewModel.productionObjectFlow.first {
            it is Resource.Success
        } as Resource.Success

        assert(firstSuccessStateResource.data == Production.movieDummy)
    }

    @Test
    fun errorStateEmittedAfterPassingNullToTheViewModel() = runBlocking {
        productionDetailsViewModel.loadProductionObject(null)

        val firstErrorStateResourceEmitted = productionDetailsViewModel.productionObjectFlow.first {
            it is Resource.Error
        } as Resource.Error

        val expectedMessageEqualsTheEmittedMessage =
            firstErrorStateResourceEmitted.message == ProductionDetailsViewModel.UNEXPECTED_ERROR_MESSAGE

        assert(expectedMessageEqualsTheEmittedMessage)
    }

    @Test
    fun loadingStateEmittedFirst() = runBlocking {
        val first = productionDetailsViewModel.productionObjectFlow.first()

        assert(first is Resource.Loading)
    }
}
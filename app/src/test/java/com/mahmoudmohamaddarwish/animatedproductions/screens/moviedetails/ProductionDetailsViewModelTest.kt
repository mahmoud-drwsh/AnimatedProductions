package com.mahmoudmohamaddarwish.animatedproductions.screens.moviedetails

import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
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
    fun `production object loads after being passed to the viewModel`() = runBlocking {
        productionDetailsViewModel.loadProductionObject(Production.dummy)

        val firstSuccessStateResource = productionDetailsViewModel.productionObjectFlow.first {
            it is Resource.Success
        } as Resource.Success

        assert(firstSuccessStateResource.data == Production.dummy)
    }

    @Test
    fun `error state emitted after passing null to the viewModel`() = runBlocking {
        productionDetailsViewModel.loadProductionObject(null)

        val firstErrorStateResourceEmitted = productionDetailsViewModel.productionObjectFlow.first {
            it is Resource.Error
        } as Resource.Error

        val expectedMessageEqualsTheEmittedMessage =
            firstErrorStateResourceEmitted.message == ProductionDetailsViewModel.UNEXPECTED_ERROR_MESSAGE

        assert(expectedMessageEqualsTheEmittedMessage)
    }

    @Test
    fun `loading state emitted first`() = runBlocking {
        val first = productionDetailsViewModel.productionObjectFlow.first()

        assert(first is Resource.Loading)
    }
}
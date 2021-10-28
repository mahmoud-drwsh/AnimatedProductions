package com.mahmoudmohamaddarwish.animatedproductions.screens.moviedetails

import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class ProductionDetailsViewModelTest {

    private lateinit var productionDetailsViewModel: ProductionDetailsViewModel

    @Before
    fun setUp() {
        productionDetailsViewModel = ProductionDetailsViewModel()
    }

    @Test
    fun `production object loads after being passed to the viewModel`() {
        productionDetailsViewModel.loadProductionObject(Production.dummy)
        runBlocking {
            val first =
                productionDetailsViewModel.productionObject.dropWhile { it !is Resource.Success }
                    .first()
            assert(first is Resource.Success)

            val value = first as Resource.Success

            assert(value.data == Production.dummy)
        }
    }

    @Test
    fun `error state emitted after passing null to the viewModel`() {
        productionDetailsViewModel.loadProductionObject(null)

        runBlocking {
            val first =
                productionDetailsViewModel.productionObject.dropWhile { it !is Resource.Error }
                    .first()

            assert(first is Resource.Error)

            val value = first as Resource.Error

            assert(value.message == ProductionDetailsViewModel.UNEXPECTED_ERROR_MESSAGE)
        }
    }
}
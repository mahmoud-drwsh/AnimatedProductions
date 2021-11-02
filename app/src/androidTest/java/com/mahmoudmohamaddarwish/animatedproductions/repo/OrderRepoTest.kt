package com.mahmoudmohamaddarwish.animatedproductions.repo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Order
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.OrderUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class OrderRepoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var orderUseCase: OrderUseCase

    @Before
    fun setup() {
        hiltRule.inject()
    }

    // these objects will be used to test that the dataStore is working correctly
    private val orderObjects = listOf(
        Order(Order.Property.RELEASE_DATE, Order.Type.DESCENDING),
        Order(Order.Property.RELEASE_DATE, Order.Type.ASCENDING),
        Order(Order.Property.Name, Order.Type.ASCENDING),
        Order(Order.Property.Name, Order.Type.DESCENDING),
    )


    @Suppress("IllegalIdentifier")
    @Test
    fun `Order objects are correctly saved and retrieved`() {
        orderObjects.forEach { order ->
            testInsertion(order)
        }
    }

    private fun testInsertion(order: Order) {
        runBlocking {
            // insert the order object
            orderUseCase.setSortProperty(order.property)
            orderUseCase.setSortType(order.type)
            // wait to ensure that it has been saved
            delay(500L)
            // getting the first emitted value after the insertion
            val first = orderUseCase.order.first()
            // making sure it is equal to the one inserted
            assert(first == order) {
                "Expected: $order, found: $first"
            }
        }
    }
}
package com.mahmoudmohamaddarwish.animatedproductions;

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Order
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.OrderingUseCase
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
class OrderingUseCaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var orderingUseCase: OrderingUseCase

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
    fun `the right Order object is returned after saving it_1`() {
        orderObjects.forEach { order ->
            testInsertion(order)
        }
    }

    private fun testInsertion(order: Order) {
        runBlocking {
            // insert the order object
            orderingUseCase.setOrderProperty(order.property)
            orderingUseCase.setOrderType(order.type)
            // wait to ensure that it has been saved
            delay(500L)
            // getting the first emitted value after the insertion
            val first = orderingUseCase.order.first()
            // making sure it is equal to the one inserted
            assert(first == order) {
                "Expected: $order, found: $first"
            }
        }
    }
}
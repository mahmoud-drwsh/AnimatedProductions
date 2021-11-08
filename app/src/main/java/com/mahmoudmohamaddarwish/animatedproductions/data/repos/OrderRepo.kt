package com.mahmoudmohamaddarwish.animatedproductions.data.repos

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.mahmoudmohamaddarwish.animatedproductions.data.datastore.Constants
import com.mahmoudmohamaddarwish.animatedproductions.di.CoroutinesScopesModule.ApplicationScope
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Order
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.OrderUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class OrderRepo @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @ApplicationScope private val appCoroutineScope: CoroutineScope,
) : OrderUseCase {

    override val order: Flow<Order> = dataStore.data.map { preferences ->
        val propertyName = preferences[Constants.orderPropertyNameKey] ?: Order.defaultProperty.name

        val orderType = preferences[Constants.orderTypeKey] ?: Order.defaultType.name

        Order(Order.Property.valueOf(propertyName), Order.Type.valueOf(orderType))
    }.catch {
        Order.default
    }

    override fun setSortProperty(propertyName: Order.Property) {
        appCoroutineScope.launch {
            dataStore.edit { preferences: MutablePreferences ->
                preferences[Constants.orderPropertyNameKey] = propertyName.name
            }
        }
    }

    override fun setSortType(type: Order.Type) {
        appCoroutineScope.launch {
            dataStore.edit { preferences: MutablePreferences ->
                preferences[Constants.orderTypeKey] = type.name
            }
        }
    }
}
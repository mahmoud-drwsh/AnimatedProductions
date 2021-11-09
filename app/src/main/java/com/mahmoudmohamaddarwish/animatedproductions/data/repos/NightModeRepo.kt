package com.mahmoudmohamaddarwish.animatedproductions.data.repos

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.mahmoudmohamaddarwish.animatedproductions.data.datastore.Constants
import com.mahmoudmohamaddarwish.animatedproductions.di.CoroutinesScopesModule.ApplicationScope
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.NightModeUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NightModeRepo @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @ApplicationScope private val coroutineScope: CoroutineScope,
) : NightModeUseCase {

    override val isNightModeEnabled: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[Constants.nightModeKey] ?: default
        }

    override fun toggleNightMode() {
        coroutineScope.launch {
            dataStore.edit {
                val newValue = (it[Constants.nightModeKey] ?: default).not()
                it[Constants.nightModeKey] = newValue
            }
        }
    }

    companion object {
        private const val default = false
    }
}
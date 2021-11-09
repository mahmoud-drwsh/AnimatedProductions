package com.mahmoudmohamaddarwish.animatedproductions.data.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey

object Constants {
    val nightModeKey: Preferences.Key<Boolean> =
        booleanPreferencesKey("orderPropertyNameKey")
}
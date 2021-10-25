package com.mahmoudmohamaddarwish.animatedproductions.data.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    internal val orderPropertyNameKey: Preferences.Key<String> =
        stringPreferencesKey("orderPropertyNameKey")

    internal val orderTypeKey: Preferences.Key<String> = stringPreferencesKey("orderTypeKey")
}
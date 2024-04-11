package com.thoughtworks.androidtrain

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

private const val DATA_STORE_ACTIVITY_PREFERENCES_KEY = "data_store_activity_preferences_key "

data class DataStoreActivityPreferences(val isHintShow: Boolean)

val Context.dataStore by preferencesDataStore(name = DATA_STORE_ACTIVITY_PREFERENCES_KEY)

object DataStoreActivityPreferencesKeys {
    val IS_HINT_SHOW = booleanPreferencesKey("is_hint_show")
}

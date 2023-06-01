package com.gilar.mynewspaper.repository

import android.content.Context
import android.util.Log
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val PREFERENCE_NAME = "settings"

class DataStoreRepository(context: Context) {

    private object PreferenceKeys {
        val country = preferencesKey<String>("country")
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCE_NAME
    )

    suspend fun saveToDataStore(country: String){
        dataStore.edit { preference ->
            preference[PreferenceKeys.country] = country
        }
    }


    val readCountryFromDataStore: Flow<String> = dataStore.data
        .catch { exception ->
            if(exception is IOException){
                Log.d("DataStore", exception.message.toString())
                emit(emptyPreferences())
            }else {
                throw exception
            }
        }
        .map { preference ->
            val country = preference[PreferenceKeys.country] ?: "in"
            country
        }
}
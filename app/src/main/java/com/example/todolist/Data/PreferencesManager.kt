package com.example.todolist.Data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

enum class SortOrder{ BY_PRAIORTY,BY_DATE }

data class FilterPreferences(val sortOrder: SortOrder)

private const val TAG = "PreferencesManager"

class PreferencesManager(public val context: Context) {

    private val dataStore=context.createDataStore("tasks_prefernces")

    val preferencesFlow=dataStore.data
        .catch { exception ->
            if (exception is IOException){
                Log.e(TAG, "error reading prefrences ",exception )
                emit(emptyPreferences())
            }
            else {
                throw exception }
        }.map { preferences ->
            val sortOrder=SortOrder.valueOf(
                preferences[Prefernces_Keys.SORT_ORDER] ?: SortOrder.BY_DATE.name
            )
            FilterPreferences(sortOrder)
        }

    suspend fun updateSortOrder(sortOrder: SortOrder) {
        dataStore.edit { preferences ->
            preferences[Prefernces_Keys.SORT_ORDER] = sortOrder.name
        }
    }
    companion object Prefernces_Keys{
        val SORT_ORDER = preferencesKey<String>("sort_order")
    }

}
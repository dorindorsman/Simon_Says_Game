package com.dorin.simonsaysgame.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.dorin.simonsaysgame.datastore.DataStoreRepository.Companion.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        const val PREFERENCE_NAME = "simon_preferences"
        const val PREFERENCE_KEY_EASY = "easy_score_state"
    }

    private object PreferenceKeys {
        val easyKey = intPreferencesKey(name = PREFERENCE_KEY_EASY)
    }

    private val dataStore = context.dataStore

    suspend fun persistEasyState(easy: Int) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.easyKey] = easy
        }
    }

    val readEasyState : Flow<Int> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw  exception
            }
        }
        .map { preferences ->
            val easyState = preferences[PreferenceKeys.easyKey] ?: 0
            easyState
        }
}
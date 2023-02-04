package com.dorin.simonsaysgame.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.dorin.simonsaysgame.datastore.DataStoreRepository.Companion.PREFERENCE_NAME
import com.dorin.simonsaysgame.menu.GameMode
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
        const val PREFERENCE_KEY_MEDIUM = "medium_score_state"
        const val PREFERENCE_KEY_HARD = "hard_score_state"
        const val PREFERENCE_KEY_CURRENT = "current_score_state"
    }

    private object PreferenceKeys {
        val easyKey = intPreferencesKey(name = PREFERENCE_KEY_EASY)
        val mediumKey = intPreferencesKey(name = PREFERENCE_KEY_MEDIUM)
        val hardKey = intPreferencesKey(name = PREFERENCE_KEY_HARD)
        val currentKey = intPreferencesKey(name = PREFERENCE_KEY_CURRENT)
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

    suspend fun persistMediumState(medium: Int) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.mediumKey] = medium
        }
    }

    val readMediumState : Flow<Int> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw  exception
            }
        }
        .map { preferences ->
            val mediumState = preferences[PreferenceKeys.mediumKey] ?: 0
            mediumState
        }

    suspend fun persistHardState(hard: Int) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.hardKey] = hard
        }
    }

    val readHardState : Flow<Int> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw  exception
            }
        }
        .map { preferences ->
            val hardState = preferences[PreferenceKeys.hardKey] ?: 0
            hardState
        }


    suspend fun persistCurrentState(gameMode: GameMode) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.currentKey] = gameMode.ordinal
        }
    }

    val readCurrentState : Flow<Int> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw  exception
            }
        }
        .map { preferences ->
            val currentState = preferences[PreferenceKeys.currentKey] ?: 0
            currentState
        }
}
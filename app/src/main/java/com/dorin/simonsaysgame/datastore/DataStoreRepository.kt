package com.dorin.simonsaysgame.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
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
        const val PREFERENCE_KEY_MEDIUM = "medium_score_state"
        const val PREFERENCE_KEY_HARD = "hard_score_state"
        const val PREFERENCE_KEY_USER_TYPE = "user_type_state"
        const val PREFERENCE_KEY_USER_PURCHASE = "user_purchase_state"
        const val PREFERENCE_KEY_USER_ADS = "user_ads_state"
    }

    private object PreferenceKeys {
        val easyKey = intPreferencesKey(name = PREFERENCE_KEY_EASY)
        val mediumKey = intPreferencesKey(name = PREFERENCE_KEY_MEDIUM)
        val hardKey = intPreferencesKey(name = PREFERENCE_KEY_HARD)
        val userTypeKey = intPreferencesKey(name = PREFERENCE_KEY_USER_TYPE)
        val userPurchaseKey = intPreferencesKey(name = PREFERENCE_KEY_USER_PURCHASE)
        val userAdsKey = intPreferencesKey(name = PREFERENCE_KEY_USER_ADS)
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


    suspend fun persistUserTypeState(type: Int) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.userTypeKey] = type
        }
    }

    val readUserTypeState : Flow<Int> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw  exception
            }
        }
        .map { preferences ->
            val userTypeState = preferences[PreferenceKeys.userTypeKey] ?: 0
            userTypeState
        }

    suspend fun persistUserAdsState(ads: Int) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.userAdsKey] = ads
        }
    }

    val readUserAdsState : Flow<Int> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw  exception
            }
        }
        .map { preferences ->
            val userAdsState = preferences[PreferenceKeys.userAdsKey] ?: 1
            userAdsState
        }
    suspend fun persistUserCoinsState(coins: Int) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.userPurchaseKey] = coins
        }
    }

    suspend fun persistUserPurchaseState(purchase: Int, coins: Int) {

        var amount = 0
        when(purchase){
            1-> {amount = 20}
            2 -> {amount = 40}
            3 -> {amount = 60}
        }

        dataStore.edit { preference ->
            preference[PreferenceKeys.userPurchaseKey] = coins + amount
        }
    }

    val readUserPurchaseState : Flow<Int> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw  exception
            }
        }
        .map { preferences ->
            val userPurchaseState = preferences[PreferenceKeys.userPurchaseKey] ?: 0
            userPurchaseState
        }

}
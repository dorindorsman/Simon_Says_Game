package com.dorin.simonsaysgame.menu

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dorin.simonsaysgame.datastore.DataStoreRepository
import com.dorin.simonsaysgame.menu.settings.FabSettingsState
import com.dorin.simonsaysgame.menu.settings.settings_menu.SettingsMenuEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class MenuViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    companion object {
        const val TAG = "MenuViewModel"
    }

    init {

    }

    var fabSettingsState by mutableStateOf(FabSettingsState.DEFAULT)
    private set


    fun handleEvent(event: MenuEvent){
        Log.d(TAG, "menu event: $event")

        when(event){
            is MenuEvent.SettingsButtonClicked -> settingsButtonClicked()
            else -> {}
        }

    }


    fun handleEvent(settingsMenuEvent: SettingsMenuEvent) {
        Log.d(TAG, "settings event: $settingsMenuEvent")

        when (settingsMenuEvent) {
            SettingsMenuEvent.Privacy -> privacyClicked()
            SettingsMenuEvent.Terms -> termsClicked()
            SettingsMenuEvent.About -> aboutClicked()
        }
        fabSettingsDismiss()
    }

    private fun fabSettingsDismiss() {
        fabSettingsState = FabSettingsState.DEFAULT
    }

    private fun settingsButtonClicked() {
        fabSettingsState = when (fabSettingsState) {
            FabSettingsState.DEFAULT -> FabSettingsState.EXPANDED
            FabSettingsState.EXPANDED -> FabSettingsState.DEFAULT
        }
    }

    private fun privacyClicked() {
        TODO("Not yet implemented")
    }

    private fun termsClicked() {
        TODO("Not yet implemented")
    }

    private fun aboutClicked() {
        TODO("Not yet implemented")
    }

    fun persistEasyState(easy: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistEasyState(easy = easy)
        }
    }

}
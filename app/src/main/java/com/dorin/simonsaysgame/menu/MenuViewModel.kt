package com.dorin.simonsaysgame.menu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dorin.simonsaysgame.datastore.DataStoreRepository
import com.dorin.simonsaysgame.menu.settings.FabSettingsState
import com.dorin.simonsaysgame.menu.settings.settings_menu.SettingsMenuEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
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

    var openAboutDialogState by mutableStateOf(false)
        private set

    var showNativeAdState by mutableStateOf(false)
        private set

    fun handleEvent(event: MenuEvent){
        Log.d(TAG, "menu event: $event")

        when(event){
            is MenuEvent.SettingsButtonClicked -> settingsButtonClicked()
            is MenuEvent.SetNativeAdState -> setNativeAdState(event.state)
            else -> {}
        }

    }
    fun handleEvent(settingsMenuEvent: SettingsMenuEvent) {
        Log.d(TAG, "settings event: $settingsMenuEvent")

        when (settingsMenuEvent) {
            is SettingsMenuEvent.Privacy -> privacyClicked(settingsMenuEvent.context)
            is SettingsMenuEvent.Terms -> termsClicked(settingsMenuEvent.context)
            is SettingsMenuEvent.About -> aboutClicked(settingsMenuEvent.openAboutDialogState)
        }
        fabSettingsDismiss()
    }

    private fun setNativeAdState(state: Boolean) {
        showNativeAdState = state
    }

    private fun fabSettingsDismiss() {
        Log.d(TAG, "fabSettingsDismiss")

        fabSettingsState = FabSettingsState.DEFAULT
    }

    private fun settingsButtonClicked() {
        Log.d(TAG, "settingsButtonClicked")

        fabSettingsState = when (fabSettingsState) {
            FabSettingsState.DEFAULT -> FabSettingsState.EXPANDED
            FabSettingsState.EXPANDED -> FabSettingsState.DEFAULT
        }
    }

    private fun privacyClicked(context: Context) {
        val url = "https://sites.google.com/view/privacy-policy-simon-game-says/%D7%91%D7%99%D7%AA"
        openUrl(context, url)
    }

    private fun termsClicked(context: Context) {
        val url = "https://sites.google.com/view/useoftermssimongamesays/%D7%91%D7%99%D7%AA"
        openUrl(context, url)
    }

    private fun aboutClicked(state : Boolean) {
        Log.d(TAG, "aboutClicked: $state")
        openAboutDialogState = state
    }
    private fun openUrl(context: Context, url: String){
        val urlIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
        context.startActivity(urlIntent)
    }
}
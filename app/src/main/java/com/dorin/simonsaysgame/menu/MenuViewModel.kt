package com.dorin.simonsaysgame.menu

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dorin.simonsaysgame.menu.settings.FabSettingsState
import com.dorin.simonsaysgame.menu.settings.settings_menu.SettingsMenuEvent


class MenuViewModel : ViewModel() {

  //  private var allItems = DataProvider.puppyList

    private var currentItemId: Int = 0

    var fabSettingsState by mutableStateOf(FabSettingsState.DEFAULT)
    private set

    companion object {
        const val TAG = "FeedViewModel"
    }


    fun handleEvent(event: MenuEvent){
        Log.d(TAG, "feed event: $event")

        when(event){
            is MenuEvent.SettingsButtonClicked -> settingsButtonClicked()
           // is MenuEvent.FeedItemClicked -> itemClicked(event.id)
        }

    }


    fun handleEvent(settingsMenuEvent: SettingsMenuEvent) {
        Log.d(TAG, "send event: $settingsMenuEvent")

        when (settingsMenuEvent) {
            SettingsMenuEvent.Privacy -> privacyClicked()
            SettingsMenuEvent.Terms -> termsClicked()
        }

        fabSettingsDismiss()
    }

    private fun fabSettingsClick() {

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



//    fun handleEvent(itemActionEvent: ItemActionEvent) {
//        Log.d(TAG, "feed item event: $itemActionEvent")
//
//        getPuppyById()?.let { puppy ->
//
//            when (itemActionEvent) {
//                ItemActionEvent.Open -> openItem(puppy)
//                ItemActionEvent.Delete -> deleteItem(puppy)
//            }
//        }
//    }

//    private fun deleteItem(puppy: Puppy) {
//        changePopUpMenuState(false)
//        allItems.remove(puppy)
//    }
//
//    private fun openItem(puppy: Puppy) {
//        changePopUpMenuState(false)
//
//    }
//
//    fun getPuppyById(): Puppy? {
//        return allItems.find {
//            it.id == currentItemId
//        }
//    }
//
//
//    private fun changePopUpMenuState(state: Boolean) {
//        fabSettingsState = state
//    }
//
//    private fun itemClicked(id: Int) {
//        Log.d(TAG, "itemClicked, id: $id")
//
//        currentItemId = id
//    }



}
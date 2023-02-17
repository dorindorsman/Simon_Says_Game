package com.dorin.simonsaysgame.menu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dorin.simonsaysgame.UserType
import com.dorin.simonsaysgame.datastore.DataStoreRepository
import com.dorin.simonsaysgame.utils.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    companion object {
        const val TAG = "MenuViewModel"
    }

    var openAboutDialogState by mutableStateOf(false)
        private set

    var interstitialAdsLoadingState by mutableStateOf(false)
        private set

    var userTypeState = MutableStateFlow<RequestState<Int>>(RequestState.Idle)
        private set

    var userType by mutableStateOf(UserType.NORMAL)
        private set

    var userPurchaseState = MutableStateFlow<RequestState<Int>>(RequestState.Idle)
        private set

    var userAdsState = MutableStateFlow<RequestState<Int>>(RequestState.Idle)
        private set

    var showAdsState by mutableStateOf(true)
        private set

    var coins by mutableStateOf(0)
        private set

    init {
        readUserTypeState()
        readUserAdsState()
        readUserPurchaseState()
    }

    fun handleEvent(event: MenuEvent){
        Log.d(TAG, "menu event: $event")

        when(event){
            is MenuEvent.SetInterstitialAdsLoadingState -> interstitialAdsLoadingState = event.state
            else -> {}
        }

    }

    private fun readUserTypeState() {
        userTypeState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readUserTypeState
                    .collect {
                        userTypeState.value = RequestState.Success(it)
                        userType = if(RequestState.Success(it).data == 1){
                            UserType.PREMIUM
                        }else{
                            UserType.NORMAL
                        }
                    }
            }
        } catch (e: Exception) {
            userTypeState.value = RequestState.Error(e)
        }
    }

    private fun readUserAdsState() {
        userAdsState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readUserAdsState
                    .collect {
                        userAdsState.value = RequestState.Success(it)
                        showAdsState = RequestState.Success(it).data == 1
                    }
            }
        } catch (e: Exception) {
            userAdsState.value = RequestState.Error(e)
        }
    }

    private fun readUserPurchaseState() {
        userPurchaseState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readUserPurchaseState
                    .collect {
                        userPurchaseState.value = RequestState.Success(it)
                        coins = RequestState.Success(it).data
                    }
            }
        } catch (e: Exception) {
            userPurchaseState.value = RequestState.Error(e)
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
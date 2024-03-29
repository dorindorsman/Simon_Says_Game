package com.dorin.simonsaysgame.gamepanel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dorin.simonsaysgame.R
import com.dorin.simonsaysgame.UserType
import com.dorin.simonsaysgame.datastore.DataStoreRepository
import com.dorin.simonsaysgame.menu.GameMode
import com.dorin.simonsaysgame.ui.theme.*
import com.dorin.simonsaysgame.utils.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.random.Random

@HiltViewModel
class PanelGameViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    companion object {
        const val TAG = "PanelGameScreenViewModel"
    }

    var rewardedAdsLoadingState by mutableStateOf(false)
        private set

    var interstitialAdsLoadingState by mutableStateOf(false)
        private set

    var giveRewardState by mutableStateOf<RewardState>(RewardState.WAIT)
        private set

    var btnColorState by mutableStateOf(Green)
        private set

    var btnSoundState by mutableStateOf(R.raw.victory)
        private set

    var highScore: Int = 0

    var coins by mutableStateOf(0)
        private set

    var gameModeState by mutableStateOf(GameMode.EASY)
        private set

    var levelState by mutableStateOf<Long>(500)
        private set

    var sortState = MutableStateFlow<RequestState<Int>>(RequestState.Idle)
        private set

    var userTypeState = MutableStateFlow<RequestState<Int>>(RequestState.Idle)
        private set

    var userType by mutableStateOf(UserType.NORMAL)
        private set

    var hintState by mutableStateOf(false)
        private set

    var userPurchaseState = MutableStateFlow<RequestState<Int>>(RequestState.Idle)
        private set

    var userAdsState = MutableStateFlow<RequestState<Int>>(RequestState.Idle)
        private set

    var showAdsState by mutableStateOf(true)
        private set

    // color state for squares
    // 0 - default (color)
    // 1 - correct (green)
    // 2 - incorrect (red)


    // 0 - default (color)
    // 1 - press (press)
    // 2 - correct (green)
    // 3 - incorrect (red)
    // have only one non-zero color state at one time ?
    var btnStates by mutableStateOf(List(4) { 0 })
        private set

    // current level
    var score by mutableStateOf(0)
        private set

    // allow player input if true
    var playerTurn by mutableStateOf(false)
        private set

    // remaining number of times player can make errors
    // game over if reaches 0
    var attemptsLeft by mutableStateOf(1)
        private set

    // indicates if the game has started yet
    var gameRunning by mutableStateOf(false)
        private set


    init {
        readEasyState()
        readUserPurchaseState()
        readUserAdsState()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun handleEvent(event: PanelGameEvent) {
        Log.d(TAG, "game event: $event")

        when (event) {
            is PanelGameEvent.SetRewardedAdsLoadingState -> rewardedAdsLoadingState = event.state
            is PanelGameEvent.SetInterstitialAdsLoadingState -> interstitialAdsLoadingState = event.state
            is PanelGameEvent.SetGiveRewardState -> giveRewardState = event.state
            is PanelGameEvent.SetButtonColorState -> btnColorState = event.color
            is PanelGameEvent.SetButtonSound -> setSound(event.index)
            is PanelGameEvent.GameModeButtonClicked -> setGameMode(event.gameMode)
            is PanelGameEvent.ReadUserTypeState -> readUserTypeState()
            is PanelGameEvent.StartRound -> startRound()
            is PanelGameEvent.ReceiveInput -> receiveInput(event.index)
            is PanelGameEvent.SetHighScore -> setHighScore()
            is PanelGameEvent.AskForHint -> askForHint()
            is PanelGameEvent.AskForLive -> askForLive()
        }
    }

    private fun setGameMode(gameMode: Int) {
        when (gameMode) {
            0 -> {
                readEasyState()
                gameModeState = GameMode.EASY
                levelState = 500
            }
            1 -> {
                readMediumState()
                gameModeState = GameMode.MEDIUM
                levelState = 200
            }
            2 -> {
                readHardState()
                gameModeState = GameMode.HARD
                levelState = 100
            }
            else -> {
                readEasyState()
                gameModeState = GameMode.EASY
                levelState = 500
            }
        }
        Log.d(TAG, gameModeState.name + " " + levelState)
    }

    // current sequence
    // each list item is within [0,3] corresponding to each square
    private val sequence: MutableList<Int> = mutableListOf()

    // current position in sequence
    private var sequenceIndex: Int = 0

    // extends the current sequence with a randomly generated integer
    private fun extendSequence() {
        sequence.add(Random.nextInt(0, 3))
    }

    // toggles on square with given id to the specified state
    // toggles off all other squares
    // e.g. id = 3, state = 1 -> [0,0,0,1,0,0,0...]
    private fun toggleButton(id: Int, state: Int): List<Int> {
        val buttons = MutableList(4) { 0 }
        buttons[id] = state
        return buttons.toList()
    }

    // toggles the entire board to the specified state
    private fun toggleBoard(state: Int): List<Int> {
        return List(4) { state }
    }

    // start new round with sequence complete indicator
    private fun newRound() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                // disable player input
                playerTurn = false
                btnStates = toggleBoard(0)
                delay(500)

                // start the new round
            }
            startRound()
        }
    }

    // replay the sequence with incorrect input indicator
    private fun replaySequence() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                // disable player input
                playerTurn = false

                // incorrect input indicator
                btnStates = toggleBoard(3)
                delay(500)
                btnStates = toggleBoard(0)
                delay(250)

                // animate sequence
                for (s in sequence) {
                    delay(levelState)
                    btnStates = toggleButton(s, 1)
                    delay(500)
                    btnStates = toggleButton(s, 0)
                }

                // enable player input
                playerTurn = true

            }
        }
    }

    /*** Public Interface ***/
    // starts a new round
    fun startRound() {
        // prepare sequence
        extendSequence()
        sequenceIndex = 0
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                // disable player input
                gameRunning = true
                playerTurn = false


                // animate sequence
                for (s in sequence) {
                    delay(levelState)
                    btnStates = toggleButton(s, 1)
                    delay(500)
                    btnStates = toggleButton(s, 0)
                }

                // re-enable player input
                playerTurn = true
            }
        }
    }

    // indicates input from button with specified id

    @RequiresApi(Build.VERSION_CODES.O)
    fun receiveInput(index: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                if (playerTurn) {
                    if (index == sequence[sequenceIndex]) {
                        // correct input
                        if (sequenceIndex == sequence.size - 1) {
                            // end of sequence
                            score++
                            btnStates = toggleBoard(2)
                            coins += 2
                            delay(3000)
                            newRound()
                        } else {
                            // advance sequence
                            sequenceIndex += 1
                        }

                    } else {
                        // incorrect input
                        sequenceIndex = 0
                        Log.d("dorin 339 model", attemptsLeft.toString())
                        attemptsLeft--
                        btnStates = toggleBoard(3)

                        if (attemptsLeft != 0) {
                            replaySequence()
                        } else {
                            // game over
                            while (giveRewardState == RewardState.WAIT) {
                            }
                            checkReward()
                        }
                        delay(3000)
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun checkReward() {
        if (attemptsLeft == 0) {
            when (giveRewardState) {
                RewardState.SHOW -> {
                    sequenceIndex = 0
                    btnStates = toggleBoard(0)
                    playerTurn = false
                    attemptsLeft = 1
                    while (rewardedAdsLoadingState) { }
                    delay(2000)
                    replaySequence()
                    giveRewardState = RewardState.SHOWED
                }
                RewardState.UNSHOW -> {
                    btnStates = toggleBoard(3)
                    playerTurn = false
                    setHighScore()
                    persistUserCoinsState(coins)
                }
                RewardState.SHOWED -> {
                    btnStates = toggleBoard(3)
                    playerTurn = false
                    setHighScore()
                    persistUserCoinsState(coins)
                }
                RewardState.WAIT -> {}
            }
        }
    }

    private fun askForHint() {
        if (userType == UserType.PREMIUM) {
            if (hintState) {
                replaySequence()
                hintState = false
            }
        }

        if (coins >= 15) {
            replaySequence()
            persistUserCoinsState(coins - 15)
            readUserPurchaseState()
            hintState = coins >= 15
        }
    }


    private fun askForLive() {
        if (coins >= 20) {
            persistUserCoinsState(coins - 20)
            readUserPurchaseState()
            attemptsLeft++
        }
    }


    fun reset() {
        sequence.clear()
        sequenceIndex = 0
        btnStates = List(4) { 0 }
        score = 0
        playerTurn = false
        readUserTypeState()
        gameRunning = false
    }

    private fun setSound(index: Int) {
        when (btnColorState) {
            Green -> R.raw.green
            Red -> R.raw.red
            Yellow -> R.raw.yellow
            LightBrightBlue -> R.raw.blue
            else -> {
                R.raw.error
            }
        }

        if (btnStates[index] == 2) {
            btnColorState = correct
            btnSoundState = R.raw.victory
        } else if (btnStates[index] == 3) {
            btnColorState = inCorrect
            btnSoundState = R.raw.error
        }
    }

    private fun setHighScore() {
        if (score > highScore) {
            highScore = score
        }

        when (gameModeState) {
            GameMode.EASY -> {
                persistEasyState(highScore)
            }
            GameMode.MEDIUM -> {
                persistMediumState(highScore)
            }
            GameMode.HARD -> {
                persistHardState(highScore)
            }
        }
    }

    private fun readEasyState() {
        sortState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readEasyState
                    .collect {
                        sortState.value = RequestState.Success(it)
                        highScore = RequestState.Success(it).data
                    }
            }
        } catch (e: Exception) {
            sortState.value = RequestState.Error(e)
        }
    }

    private fun readMediumState() {
        sortState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readMediumState
                    .collect {
                        sortState.value = RequestState.Success(it)
                        highScore = RequestState.Success(it).data
                    }
            }
        } catch (e: Exception) {
            sortState.value = RequestState.Error(e)
        }
    }

    private fun readHardState() {
        sortState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readHardState
                    .collect {
                        sortState.value = RequestState.Success(it)
                        highScore = RequestState.Success(it).data
                    }
            }
        } catch (e: Exception) {
            sortState.value = RequestState.Error(e)
        }
    }

    private fun persistEasyState(highScore: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistEasyState(easy = highScore)
        }
    }

    private fun persistMediumState(highScore: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistMediumState(medium = highScore)
        }
    }

    private fun persistHardState(highScore: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistHardState(hard = highScore)
        }
    }

    private fun readUserTypeState() {
        userTypeState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readUserTypeState
                    .collect {
                        userTypeState.value = RequestState.Success(it)
                        userType = if (RequestState.Success(it).data == 1) {
                            hintState = true
                            attemptsLeft = 3
                            UserType.PREMIUM
                        } else {
                            hintState = false
                            attemptsLeft = 1
                            UserType.NORMAL
                        }
                    }
            }
        } catch (e: Exception) {
            userTypeState.value = RequestState.Error(e)
        }
    }

    fun readUserPurchaseState() {
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

    fun persistUserCoinsState(coins: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistUserCoinsState(coins = coins)
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

}
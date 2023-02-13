package com.dorin.simonsaysgame.gamepanel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dorin.simonsaysgame.R
import com.dorin.simonsaysgame.datastore.DataStoreRepository
import com.dorin.simonsaysgame.menu.GameMode
import com.dorin.simonsaysgame.ui.theme.*
import com.dorin.simonsaysgame.utils.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
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

    var viewState by mutableStateOf(ViewState())
        private set

    var btnColorState by mutableStateOf<Color>(Green)
        private set

    var btnSoundState by mutableStateOf<Int>(R.raw.victory)
        private set

    var highScore: Int = 0

    var gameModeState by mutableStateOf(GameMode.EASY)
        private set

    var levelState by mutableStateOf<Long>(500)
        private set

    var sortState = MutableStateFlow<RequestState<Int>>(RequestState.Idle)
        private set

    init {
        readEasyState()
    }

    fun handleEvent(event: PanelGameEvent) {
        Log.d(TAG, "game event: $event")

        when (event) {
            is PanelGameEvent.SetRewardedAdsLoadingState -> rewardedAdsLoadingState = event.boolean
            is PanelGameEvent.SetButtonColorState -> setColor(event.color)
            is PanelGameEvent.SetButtonSound -> setSound(event.index)
            is PanelGameEvent.GameModeButtonClicked -> setGameMode(event.gameMode)
            is PanelGameEvent.SetHighScore -> setHighScore()
            else -> {}
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
        Log.d(TAG, gameModeState.name +" "+levelState)
    }

    // current sequence
    // each list item is within [0,3] corresponding to each square
    private val sequence: MutableList<Int> = mutableListOf()

    // current position in sequence
    private var sequenceIndex: Int = 0

    // Public game state
    data class ViewState(
        // color state for squares
        // 0 - default (color)
        // 1 - correct (green)
        // 2 - incorrect (red)


        // 0 - default (color)
        // 1 - press (press)
        // 2 - correct (green)
        // 3 - incorrect (red)
        // have only one non-zero color state at one time ?
        val btnStates: List<Int> = List(4) { 0 },

        // current level
        val score: Int = 0,

        // allow player input if true
        val playerTurn: Boolean = false,

        // remaining number of times player can make errors
        // game over if reaches 0
        var attemptsLeft: Int = 3,

        // indicates if the game has started yet
        var gameRunning: Boolean = false
    )

    /*** Private functions ****/

    // emits state for recomposition
    private fun emit(state: ViewState) {
        viewState = state
    }

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
                emit(
                    viewState.copy(
                        playerTurn = false
                    )
                )
                emit(
                    viewState.copy(
                        btnStates = toggleBoard(0)
                    )
                )
                delay(500)

                // start the new round
                startRound()
            }
        }
    }

    // replay the sequence with incorrect input indicator
    private fun replaySequence() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                // disable player input
                emit(
                    viewState.copy(
                        playerTurn = false
                    )
                )

                // incorrect input indicator
                emit(
                    viewState.copy(
                        btnStates = toggleBoard(3)
                    )
                )
                delay(500)
                emit(
                    viewState.copy(
                        btnStates = toggleBoard(0)
                    )
                )
                delay(250)

                // animate sequence
                for (s in sequence) {
                    delay(levelState)
                    emit(
                        viewState.copy(
                            btnStates = toggleButton(s, 1)
                        )
                    )
                    delay(500)
                    emit(
                        viewState.copy(
                            btnStates = toggleButton(s, 0)
                        )
                    )
                }

                // enable player input
                emit(
                    viewState.copy(
                        playerTurn = true
                    )
                )
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
                emit(
                    viewState.copy(
                        gameRunning = true,
                        playerTurn = false
                    )
                )

                // animate sequence
                for (s in sequence) {
                    delay(levelState)
                    emit(
                        viewState.copy(
                            btnStates = toggleButton(s, 1)
                        )
                    )
                    delay(500)
                    emit(
                        viewState.copy(
                            btnStates = toggleButton(s, 0)
                        )
                    )
                }

                // re-enable player input
                emit(
                    viewState.copy(
                        playerTurn = true
                    )
                )
            }
        }
    }

    // indicates input from button with specified id
    fun receiveInput(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                if (viewState.playerTurn) {
                    if (id == sequence[sequenceIndex]) {
                        // correct input
                        if (sequenceIndex == sequence.size - 1) {
                            // end of sequence
                            emit(
                                viewState.copy(
                                    score = viewState.score + 1,
                                    btnStates = toggleBoard(2)
                                )
                            )
                            delay(3000)
                            newRound()
                        } else {
                            // advance sequence
                            sequenceIndex += 1
                        }
                    } else {
                        // incorrect input
                        sequenceIndex = 0
                        emit(
                            viewState.copy(
                                attemptsLeft = viewState.attemptsLeft - 1,
                                btnStates = toggleBoard(3)
                            )
                        )
                        if (viewState.attemptsLeft != 0) {
                            replaySequence()
                        } else {
                            // game over
                            emit(
                                viewState.copy(
                                    btnStates = toggleBoard(3),
                                    playerTurn = false
                                )
                            )
                            setHighScore()
                        }
                        delay(3000)
                    }
                }
            }
        }
    }

    fun reset() {
        sequence.clear()
        sequenceIndex = 0
        emit(
            ViewState()
        )
    }

    private fun setColor(color: Color) {
        btnColorState = color
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

        if (viewState.btnStates[index] == 2) {
            btnColorState = correct
            btnSoundState = R.raw.victory
        } else if (viewState.btnStates[index] == 3) {
            btnColorState = inCorrect
            btnSoundState = R.raw.error
        }
    }

    private fun setHighScore() {
        if (viewState.score > highScore) {
            highScore = viewState.score
        }

        when(gameModeState){
            GameMode.EASY -> { persistEasyState(highScore) }
            GameMode.MEDIUM -> { persistMediumState(highScore) }
            GameMode.HARD -> {persistHardState(highScore) }
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
            dataStoreRepository.persistEasyState( easy = highScore)
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
}
package com.dorin.simonsaysgame.gamePanel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlin.random.Random


class PanelGameScreenViewModel : ViewModel() {

    var rewardedAdsLoadingState by mutableStateOf(false)
        private set

    var viewState by mutableStateOf(ViewState())
        private set

    companion object {
        const val TAG = "PanelGameScreenViewModel"
    }


    fun handleEvent(event: PanelGameEvent) {
        Log.d(TAG, "game event: $event")

        when (event) {
            is PanelGameEvent.SetRewardedAdsLoadingState -> rewardedAdsLoadingState = event.boolean
        }
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
        val attemptsLeft: Int = 3,

        // indicates if the game has started yet
        val gameRunning: Boolean = false
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

                // sequence complete indicator
//                emit(
//                    viewState.copy(
//                        btnStates = toggleBoard(1)
//                    )
//                )
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
                    delay(500)
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
                    delay(500)
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


}
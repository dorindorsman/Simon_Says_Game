package com.dorin.simonsaysgame.score


data class Score(val gameId: Int, val gameScore: Int, val gameDate: String){
    val id :Int = gameId
    val score: Int = gameScore
}


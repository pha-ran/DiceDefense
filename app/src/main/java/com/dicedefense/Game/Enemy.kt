package com.dicedefense.Game

class Enemy(val size : Float, var y : Float, var hp : Int) {
    fun getBoundaryBot() : Float {
        return y + size
    }
}
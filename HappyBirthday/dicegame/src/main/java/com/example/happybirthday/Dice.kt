package com.example.happybirthday

/**
 * Logic to roll a dice
 */
class Dice(private val sides: Int) {
    /**
     * Generate and return a number between 1 and 6.
     */
    fun roll() = when ((1..sides).random()) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
}

/**
 * File:    DiceActivity.kt
 * Created: July 15, 2021
 * Author:  Mitul Vaghamshi
 * Email:   mitulvaghmashi@gmail.com
 */
package com.example.happybirthday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.happybirthday.databinding.ActivityDiceBinding

/**
 * This activity allows user to roll a dice
 * and view the result in the screen.
 */
class DiceActivity : AppCompatActivity() {
    // Create an object of Dice with 6 sides.
    private val dice = Dice(6)
    private lateinit var binding: ActivityDiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiceBinding.inflate(layoutInflater)
        with(binding) {
            setContentView(root)
            buttonRoll.setOnClickListener { imageViewDice.setImageResource(dice.roll()) }
        }
    }
}

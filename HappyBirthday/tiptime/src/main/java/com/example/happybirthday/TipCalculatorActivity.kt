package com.example.happybirthday

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.happybirthday.databinding.ActivityTipCalculatorBinding
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import kotlin.math.ceil

class TipCalculatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTipCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonCalculate.setOnClickListener { calculateTip() }
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view, keyCode)
        }
    }

    private fun calculateTip() {
        with(binding) {
            try {
                val cost = costOfServiceEditText.text.toString().toDouble()
                val tipPercentage = when (radioGroupTip.checkedRadioButtonId) {
                    radioButtonTip15p.id -> 0.15
                    radioButtonTip18p.id -> 0.18
                    radioButtonTip20p.id -> 0.20
                    else -> {
                        Snackbar.make(
                            root, "No Tip!!!, please select tip percentage!",
                            Snackbar.LENGTH_LONG
                        ).show()
                        0.0
                    }
                }
                var tip = cost * tipPercentage
                if (switchRoundupTip.isChecked) tip = ceil(tip)
                NumberFormat.getCurrencyInstance().format(tip).also {
                    textViewTipAmount.text = getString(R.string.tip_amount, it)
                }
            } catch (e: NumberFormatException) {
                Snackbar.make(
                    root, "Did you missed to enter the service cost?",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}

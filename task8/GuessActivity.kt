package com.example.guessnumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_guess.*

class GuessActivity : AppCompatActivity() {
    var start: Int = 0
    var end: Int = 0
    var middle: Int = 0

    var rangeView: TextView? = null
    var questionView: TextView? = null

    fun guess() {
        if (end - start == 1) {
            middle = start
            questionView?.text = "Ваше число $start?"
        } else {
            middle = (end + start) / 2
            questionView?.text = "Ваше число больше $middle?"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess)

        start = intent.extras?.get("start") as Int
        end = intent.extras?.get("end") as Int

        rangeView = findViewById(R.id.rangeView)
        questionView = findViewById(R.id.questionView)
        rangeView?.text = "[$start, $end]"

        guess()
    }

    fun onYesClick(view: View) {
        if (middle == start) {
            questionView?.text = "Вы загадали $start"
            yesButton.isEnabled = false
            noButton.isEnabled = false
            rangeView?.text = ""
        } else {
            start = middle
            rangeView?.text = "[$start, $end]"
            guess()
        }
    }

    fun onNoClick(view: View) {
        end = middle
        rangeView?.text = "[$start, $end]"

        if (middle == start) {
            questionView?.text = "Вы загадали ${start + 1}"
            yesButton.isEnabled = false
            noButton.isEnabled = false
            rangeView?.text = ""
        } else {
            guess()
        }
    }
}
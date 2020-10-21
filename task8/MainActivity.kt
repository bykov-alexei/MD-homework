package com.example.guessnumber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var input1: EditText? = null
    var input2: EditText? = null
    var rangeView: TextView? = null
    var yesButton: TextView? = null
    var noButton: TextView? = null

    var start: Int = 0;
    var end: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input1 = findViewById(R.id.startNumber)
        input2 = findViewById(R.id.endNumber)
        rangeView = findViewById(R.id.rangeView)
        yesButton = findViewById(R.id.yesButton)
        noButton = findViewById(R.id.noButton)

        input1?.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != "") {
                    try {
                        this@MainActivity.start = s.toString().toInt()
                    } catch (e: java.lang.NumberFormatException) {
                        input1?.setText("")
                        Toast.makeText(this@MainActivity, "Ошибка при вводе первого числа. Попробуйте снова", Toast.LENGTH_SHORT).show()
                    }
                    rangeView?.text = "[${this@MainActivity.start}, $end]"
                }
            }
        })

        input2?.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != "") {
                    try {
                        this@MainActivity.end = s.toString().toInt()
                    } catch (e: NumberFormatException) {
                        input2?.setText("")
                        Toast.makeText(this@MainActivity, "Ошибка при вводе второго числа. Попробуйте снова", Toast.LENGTH_SHORT).show()
                    }
                    rangeView?.text = "[${this@MainActivity.start}, $end]"
                }
            }
        })
    }

    fun onBtnClick(btn: View) {
        if (input1?.text.toString().trim() == "") {
            Toast.makeText(this, "Введите первое число", Toast.LENGTH_SHORT).show()
            return
        }
        if (input2?.text.toString().trim() == "") {
            Toast.makeText(this, "Введите второе число", Toast.LENGTH_SHORT).show()
            return
        }

        start = input1?.text.toString().toInt()
        end = input2?.text.toString().toInt()
        if (end <= start) {
            Toast.makeText(this,"Второе число должно быть больше первого", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(this, GuessActivity::class.java).apply {
            putExtra("start", start)
            putExtra("end", end)
        }
        startActivity(intent)
    }
}
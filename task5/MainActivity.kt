package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var films: MutableList<String> = mutableListOf()
    private var ptr = -1;

    fun btnClick(view: View) {
        val filmView: TextView = findViewById(R.id.filmView)
        ptr += 1;
        if (ptr != films.size)
            filmView.text = films[ptr]
        else {
            loadFilms()
            filmView.text = films[ptr]
        }
    }

    private fun loadFilms() {
        ptr = 0
        films = resources.getStringArray(R.array.films).toMutableList()
        films.shuffle();
        films.add("We don't have more films. Click to start over")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFilms()
    }
}
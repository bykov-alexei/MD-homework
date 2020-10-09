package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson

class Film(
    val title: String,
    val description: String,
    val imgPath: String,
    val year: Int,
    val directedBy: String,
    val genre: String
)

class FilmList(
    val films: ArrayList<Film>
)

class MainActivity : AppCompatActivity() {

    private var films: ArrayList<Film> = arrayListOf()
    private var ptr = 0;

    private fun drawFilm(film: Film) {
        val filmTitleView: TextView = findViewById(R.id.filmTitle)
        val filmDescriptionView: TextView = findViewById(R.id.filmDescription)
        val filmYearView: TextView = findViewById(R.id.filmYear)
        val filmGenreView: TextView = findViewById(R.id.filmGenre)
        val filmDirectedByView: TextView = findViewById(R.id.filmDirection)
        val filmImage: ImageView = findViewById(R.id.filmImage)

        filmTitleView.text = film.title
        filmDescriptionView.text = film.description
        filmYearView.text = film.year.toString()
        filmGenreView.text = film.genre
        filmDirectedByView.text = film.directedBy
        filmImage.setImageResource(resources.getIdentifier(film.imgPath, "drawable", packageName))
    }

    private fun setVisibility(visibility: Int) {
        val ids = arrayOf(R.id.filmDescription, R.id.filmDirection, R.id.filmGenre, R.id.filmYear, R.id.filmImage)
        for (id in ids) {
            val view: View = findViewById(id)
            view.visibility = visibility
        }
    }

    private fun setTitle(title: String) {
        val filmTitleView: TextView = findViewById(R.id.filmTitle)
        filmTitleView.text = title
    }

    fun btnClick(view: View) {
        when (ptr) {
            films.size -> {
                setVisibility(View.INVISIBLE)
                setTitle("We've ran out of films. Click the button to start over")
                films.shuffle()
                ptr = -1
            }
            else -> {
                setVisibility(View.VISIBLE)
                drawFilm(films[ptr])
            }
        }
        ptr += 1
    }

    private fun readFilms() {
        val gson = Gson()
        val filmList = gson.fromJson(assets.open("films.json").bufferedReader(), FilmList::class.java)
        films = filmList.films
        films.shuffle()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        readFilms()
        setVisibility(View.INVISIBLE)
        setTitle("Click to start observe the films")
    }
}
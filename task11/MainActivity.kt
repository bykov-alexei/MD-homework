package com.example.customadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private var adapter: CustomAdapter? = null
    private var listView: ListView? = null
    private val persons: ArrayList<Person> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.list)

        val g = Gson()
        val loaded = g.fromJson(assets.open("people.json").bufferedReader(), PersonList::class.java)
        persons.addAll(loaded.people)

        applyAdapter(persons)
    }

    private fun applyAdapter(persons: ArrayList<Person>) {
        adapter = CustomAdapter(this, persons)
        listView?.adapter = adapter
    }

    fun sortByName(v: View) {
        persons.sortBy {
            it.name
        }
        applyAdapter(persons)
    }

    fun sortBySex(v: View) {
        persons.sortBy {
            when (it.sex) {
                Sex.MAN -> 0
                Sex.WOMAN -> 1
                Sex.UNKNOWN -> 2
            }
        }
        applyAdapter(persons)
    }

    fun sortByPhone(v: View) {
        persons.sortBy {
            it.phone
        }
        applyAdapter(persons)
    }
}
package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import java.lang.Exception

class AddNoteIntent : AppCompatActivity() {
    lateinit var titleEditView: EditText
    lateinit var priceEditView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note_intent)

        titleEditView = findViewById(R.id.titleEdit)
        priceEditView = findViewById(R.id.priceEdit)
    }

    fun saveNote(view: View) {
        val db = DatabaseHandler(this)

        val title = titleEditView.text.toString()
        var price = 0.0
        try {
            price = priceEditView.text.toString().toDouble()
        } catch (e: Exception) {
            Toast.makeText(this, "You've entered a wrong number", Toast.LENGTH_SHORT).show()
            return
        }
        val note = Note(title, price, null)
        db.saveNote(note)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
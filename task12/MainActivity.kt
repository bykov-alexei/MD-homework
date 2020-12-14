package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class Note(val title: String, val price: Double, val createdAt: String?) {
    companion object {
        const val TABLE_NAME = "notes"
        const val PRIMARY_KEY = "id"
        const val TITLE_COLUMN = "title"
        const val PRICE_COLUMN = "price"
        const val CREATED_AT_COLUMN = "created_at"
    }
}

class NotesAdapter(private val dataSet: List<Note>): RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.titleView)
        val priceView: TextView = view.findViewById(R.id.priceView)
        val createdAtView: TextView = view.findViewById(R.id.createdAtView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleView.text = dataSet[position].title
        holder.priceView.text = dataSet[position].price.toString()
        holder.createdAtView.text = "(${dataSet[position].createdAt})"
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var listView: RecyclerView
    private lateinit var totalView: TextView
    private lateinit var countView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DatabaseHandler(this)

        val notes = db.allNotes()

        totalView = findViewById(R.id.totalSum)
        listView = findViewById(R.id.listView)
        countView = findViewById(R.id.count)
        listView.layoutManager = LinearLayoutManager(this)
        listView.adapter = NotesAdapter(notes)
        totalView.text = "Total: ${db.notesSum()}"
        countView.text = "Count: ${db.notesCount()}"
    }

    fun addNoteIntent(view: View) {
        val intent = Intent(this, AddNoteIntent::class.java)
        startActivity(intent)
    }
}
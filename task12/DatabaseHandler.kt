package com.example.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "NotesDatabase"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = """CREATE TABLE ${Note.TABLE_NAME} (
            ${Note.PRIMARY_KEY} INTEGER PRIMARY KEY,
            ${Note.TITLE_COLUMN} VARCHAR(255),
            ${Note.PRICE_COLUMN} DOUBLE,
            ${Note.CREATED_AT_COLUMN} TIMESTAMP DEFAULT CURRENT_TIMESTAMP)
        """.trimMargin()
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS ${Note.TABLE_NAME}")
        onCreate(db)
    }

    fun saveNote(note: Note): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Note.TITLE_COLUMN, note.title)
        contentValues.put(Note.PRICE_COLUMN, note.price)
        val result = db.insert(Note.TABLE_NAME, null, contentValues)
        db.close()
        return result
    }

    fun allNotes(): List<Note> {
        val notes = ArrayList<Note>()
        val query = "SELECT * FROM ${Note.TABLE_NAME} ORDER BY id"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: SQLException) {
            db.execSQL(query)
            return ArrayList()
        }

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndex(Note.TITLE_COLUMN))
                val price = cursor.getDouble(cursor.getColumnIndex(Note.PRICE_COLUMN))
                val createdAt = cursor.getString(cursor.getColumnIndex(Note.CREATED_AT_COLUMN))
                val note = Note(title, price, createdAt)
                notes.add(note)
            } while (cursor.moveToNext())
        }
        return notes
    }

    fun notesSum(): Double {
        val query = "SELECT SUM(${Note.PRICE_COLUMN}) FROM ${Note.TABLE_NAME}"
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: SQLException) {
            return 0.0
        }
        if (cursor.moveToFirst()) {
            return cursor.getDouble(0)
        }
        return 0.0
    }

    fun notesCount(): Int {
        val query = "SELECT COUNT(${Note.PRICE_COLUMN}) FROM ${Note.TABLE_NAME}"
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: SQLException) {
            return 0
        }
        if (cursor.moveToFirst()) {
            return cursor.getInt(0)
        }
        return 0
    }
}
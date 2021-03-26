package com.example.myapplication

import android.database.Cursor
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import layout.ProductsAdapter


class MainActivity : AppCompatActivity() {
    var lv: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lv = findViewById(R.id.products_list)

        val helper = DBHelper(this)
        val db = helper.writableDatabase
        val c: Cursor? = db.rawQuery("SELECT * FROM products", null)


        val adapter = ProductsAdapter(this, c, 0)
        lv!!.adapter = adapter
    }
}
package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        // выполняется, если базы данных нет
        db.execSQL(CREATE)
        db.execSQL("INSERT INTO products (title, date, price, img) VALUES ('Chair', '2020-02-01', 1000, 'https://images-na.ssl-images-amazon.com/images/I/81J5r9dANGL._SL1500_.jpg' ), ('Table', '2020-02-02', 2000, 'https://www.ikea.com/my/en/images/products/ingo-table-pine__0737092_pe740877_s5.jpg?f=s')")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // выполняется, если версия базы данных отличается
        db.execSQL("DROP TABLE products")
        onCreate(db)
    }

    companion object {
        const val DB_NAME = "products.db"
        const val TABLE_NAME = "products"
        const val CREATE =
            "CREATE TABLE $TABLE_NAME( `_id` INTEGER PRIMARY KEY AUTOINCREMENT, `title` TEXT NOT NULL, `price` INT NOT NULL, `img` TEXT NOT NULL, `date` DATE NOT NULL )"

        // при изменении структуры БД меняется и версия
        private const val DATABASE_VERSION = 12
    }
}
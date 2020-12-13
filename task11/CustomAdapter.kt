package com.example.customadapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlin.collections.ArrayList

class CustomAdapter(val context: Context, private val users: ArrayList<Person>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val person: Person = users.get(position)
        val convertView = LayoutInflater.from(context).inflate(R.layout.useritem, parent, false)
        val userPicView: ImageView = convertView.findViewById(R.id.userpic)
        convertView.setOnClickListener {
            it.setBackgroundColor(Color.RED)
        }
        val nameView: TextView = convertView.findViewById(R.id.name)
        val phoneView: TextView = convertView.findViewById(R.id.phone)

        nameView.text = person.name
        phoneView.text = person.phone
        when (person.sex) {
            Sex.MAN -> userPicView.setImageResource(R.drawable.user_man)
            Sex.WOMAN -> userPicView.setImageResource(R.drawable.user_woman)
            Sex.UNKNOWN -> userPicView.setImageResource(R.drawable.user_unknown)
        }
        return convertView
    }

    override fun getItem(position: Int): Any {
        return users[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return users.size
    }

}
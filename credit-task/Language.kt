package com.example.credittask

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SpinnerAdapter

data class LanguageResponse (
    val translation: Map<String, Language>
)

data class Language (
    val name: String,
    val nativeName: String,
)

package com.example.customadapter

import com.google.gson.annotations.SerializedName

enum class Sex {
    @SerializedName("MAN")
    MAN,
    @SerializedName("WOMAN")
    WOMAN,
    @SerializedName("UNKNOWN")
    UNKNOWN
}

class Person (val name: String, val sex: Sex, val phone: String)

class PersonList (val people: ArrayList<Person>)
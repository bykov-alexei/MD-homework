package com.example.credittask

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var languageView: Spinner
        lateinit var textField: EditText
        lateinit var translationField: TextView

        var language = "en"

        lateinit var languages: Map<String, Language>

        lateinit var plainLanguages: ArrayList<String>
        lateinit var languageCodes: ArrayList<String>
    }
    val retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Queries.API_URL)
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        languageView = findViewById(R.id.languageView)
        textField = findViewById(R.id.textView)
        translationField = findViewById(R.id.translationView)

        languageView.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                language = languageCodes[position]
            }
        }

        Queries.context = this
        loadLanguages()
    }

    class TranslationCallback: Callback<TranslationResponse> {
        override fun onResponse(call: Call<TranslationResponse>, response: Response<TranslationResponse>) {
            if (response.isSuccessful) {
                translationField.text = response.body()!![0].translations[0].text
            } else {
                Toast.makeText(Queries.context, "Произошла Ошибка :(", Toast.LENGTH_SHORT).show()
                Log.d("translation", "error: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<TranslationResponse>, t: Throwable) {
            Toast.makeText(Queries.context, "Произошла Ошибка :(", Toast.LENGTH_SHORT).show()
            Log.d("translation", "error: ${t.localizedMessage}")
        }

    }

    class LanguagesCallback: Callback<LanguageResponse> {

        override fun onResponse(call: Call<LanguageResponse>, response: Response<LanguageResponse>) {
            if (response.isSuccessful) {
                languages = response.body()!!.translation

                plainLanguages = arrayListOf()
                languageCodes = arrayListOf()
                for ((code, language) in languages) {
                    plainLanguages.add(language.name)
                    languageCodes.add(code)
                }

                ArrayAdapter(
                    Queries.context,
                    android.R.layout.simple_spinner_dropdown_item,
                    plainLanguages
                ).also { adapter ->
                    languageView.adapter = adapter
                }

                Log.d("languages", languages.getValue("en").nativeName)
            } else {
                Toast.makeText(Queries.context, "Произошла Ошибка :(", Toast.LENGTH_SHORT).show()
                Log.d("languages", "error: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<LanguageResponse>, t: Throwable) {
            Toast.makeText(Queries.context, "Произошла Ошибка :(", Toast.LENGTH_SHORT).show()
            Log.d("languages", "error: ${t.localizedMessage}")
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadLanguages() {
        val api = retrofit.create(Queries.Languages::class.java)
        val call = api.getLanguages()
        call.enqueue(LanguagesCallback())
    }

    fun onTranslateClick(v: View) {
        val text = textField.text.toString()
        val api = retrofit.create(Queries.Translate::class.java)
        val call = api.translate(language, "[{\'Text\': \'$text\'}]")
        call.enqueue(TranslationCallback())
    }

}
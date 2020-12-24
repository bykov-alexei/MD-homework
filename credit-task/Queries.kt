package com.example.credittask

import android.content.Context
import retrofit2.Call
import retrofit2.http.*

class Queries {

    companion object {
        const val API_URL = "https://api.cognitive.microsofttranslator.com"
        const val region = "eastasia"
        const val key = "43a4d51635ab42ef99ac9741131b146a"
        lateinit var context: Context
    }

    interface Translate {
        @POST("/translate")
        @Headers(
            "Content-type: application/json",
            "Ocp-Apim-Subscription-Key: $key",
            "Ocp-Apim-Subscription-Region: $region",
        )

        fun translate(@Query("to") lang: String, @Body body: String, @Query("api-version") api_version: String ="3.0"): Call<TranslationResponse>
    }

    interface Languages {
        @GET("/languages?api-version=3.0&scope=translation")
        fun getLanguages(): Call<LanguageResponse>
    }

}
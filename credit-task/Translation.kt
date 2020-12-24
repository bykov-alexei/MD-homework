package com.example.credittask

class TranslationResponse: ArrayList<DetectedLanguage>()

class DetectedLanguage(
    val translations: ArrayList<Translation>
)

class Translation (
    val text: String
)
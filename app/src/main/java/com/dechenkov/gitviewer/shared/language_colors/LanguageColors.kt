package com.dechenkov.gitviewer.shared.language_colors

import android.content.Context
import android.graphics.Color
import com.dechenkov.gitviewer.R
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class LanguageColors (
    context: Context
) : ILanguageColors {
    private val _colorsDict = context.resources.openRawResource(R.raw.language_colors).bufferedReader().use {
        it.readText()
    }.let {
        Json.decodeFromString<Map<String, String?>>(it)
    }

    override fun getColor(language: String): Int?
            = _colorsDict[language]?.let { Color.parseColor(it) }
}
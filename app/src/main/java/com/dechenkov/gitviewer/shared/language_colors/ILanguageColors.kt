package com.dechenkov.gitviewer.shared.language_colors


interface ILanguageColors {
    fun getColor(language: String): Int?
}
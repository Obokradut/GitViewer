package com.dechenkov.gitviewer.shared.models

data class Repo (
    val owner: String,
    val name: String,
    val description: String?,
    val language: String,
    val color: Int?
)


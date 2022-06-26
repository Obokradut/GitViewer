package com.dechenkov.gitviewer.shared.models

import kotlinx.serialization.Serializable

@Serializable
data class Readme(
    val content: String
)

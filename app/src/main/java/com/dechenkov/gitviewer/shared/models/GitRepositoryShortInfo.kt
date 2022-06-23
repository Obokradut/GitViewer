package com.dechenkov.gitviewer.shared.models

import kotlinx.serialization.Serializable

@Serializable
data class GitRepositoryShortInfo(
    val owner: String,
    val title: String,
    val description: String?
)

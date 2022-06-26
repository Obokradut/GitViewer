package com.dechenkov.gitviewer.shared.models

import kotlinx.serialization.Serializable

@Serializable
data class GitRepositoryShortInfo(
    val owner: UserInfoResponse,
    val name: String,
    val description: String?,
    val language: String?,
)

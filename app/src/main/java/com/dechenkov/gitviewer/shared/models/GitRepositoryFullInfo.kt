package com.dechenkov.gitviewer.shared.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitRepositoryFullInfo(
    val owner: String,
    val title: String,
    val description: String?,
    val htmlUrl: String,
    @SerialName("stargazers_count")
    val stargazersCount: Int,
    @SerialName("forks_count")
    val forksCount: Int,
    @SerialName("watchers_count")
    val watchersCount: Int,
)

package com.dechenkov.gitviewer.shared.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitRepositoryFullInfo(
    val owner: UserInfo,
    val name: String,
    val description: String?,
    val license: License?,
    @SerialName("html_url")
    val htmlUrl: String,
    @SerialName("stargazers_count")
    val stargazersCount: Int,
    @SerialName("forks_count")
    val forksCount: Int,
    @SerialName("watchers_count")
    val watchersCount: Int,
)

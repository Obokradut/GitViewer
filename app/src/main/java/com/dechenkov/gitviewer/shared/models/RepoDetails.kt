package com.dechenkov.gitviewer.shared.models

data class RepoDetails(
    val owner: String,
    val name: String,
    val url: String,
    val license: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val watchersCount: Int,
)

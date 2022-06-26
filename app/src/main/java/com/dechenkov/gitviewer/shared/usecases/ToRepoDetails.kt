package com.dechenkov.gitviewer.shared

import com.dechenkov.gitviewer.shared.models.GitRepositoryFullInfo
import com.dechenkov.gitviewer.shared.models.RepoDetails

fun GitRepositoryFullInfo.toRepoDetails() = RepoDetails(
    owner = this.owner.login,
    name = this.name,
    license = this.license?.name ?: "",
    url = this.htmlUrl,
    stargazersCount = this.stargazersCount,
    forksCount = this.forksCount,
    watchersCount = this.watchersCount,
)
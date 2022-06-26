package com.dechenkov.gitviewer.shared

import com.dechenkov.gitviewer.shared.models.GitRepositoryShortInfo
import com.dechenkov.gitviewer.shared.models.Repo

fun GitRepositoryShortInfo.toRepo() = Repo(
    owner = this.owner.login,
    name = this.name,
    description = this.description ?: "",
    language = this.language ?: "",
    color = null
)
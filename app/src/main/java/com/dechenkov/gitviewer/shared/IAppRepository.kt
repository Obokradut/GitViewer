package com.dechenkov.gitviewer.shared

import com.dechenkov.gitviewer.shared.models.Repo
import com.dechenkov.gitviewer.shared.models.RepoDetails
import com.dechenkov.gitviewer.shared.models.UserInfo

interface IAppRepository {
    suspend fun getRepositories(): List<Repo>
    suspend fun getRepository(repoId: String): RepoDetails
    suspend fun getRepositoryReadme(ownerName: String, repositoryName: String, branchName: String): String
    suspend fun signIn(token: String): UserInfo
    fun logout()
}
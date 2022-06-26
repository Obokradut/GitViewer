package com.dechenkov.gitviewer.shared.app

import com.dechenkov.gitviewer.shared.models.Repo
import com.dechenkov.gitviewer.shared.models.RepoDetails
import com.dechenkov.gitviewer.shared.models.UserInfo

interface IAppRepository {
    suspend fun getRepositories(): List<Repo>
    suspend fun getRepository(owner: String, repository: String): RepoDetails
    suspend fun getRepositoryReadme(ownerName: String, repositoryName: String, branchName: String? = null): String
    suspend fun getUrlImage(owner: String, repository: String, path: String): String
    suspend fun signIn(token: String): UserInfo
    fun logout()
}
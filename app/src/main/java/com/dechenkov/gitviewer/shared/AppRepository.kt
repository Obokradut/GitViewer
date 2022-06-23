package com.dechenkov.gitviewer.shared

import com.dechenkov.gitviewer.shared.gitApi.IGitApi
import com.dechenkov.gitviewer.shared.models.Repo
import com.dechenkov.gitviewer.shared.models.RepoDetails
import com.dechenkov.gitviewer.shared.models.UserInfo
import com.dechenkov.gitviewer.shared.toUserInfo

class AppRepository(
    private val gitApi: IGitApi,
    private val keyValueStorage: IKeyValueStorage
) : IAppRepository {
    override suspend fun getRepositories(): List<Repo> {
        TODO("Not yet implemented")
    }

    override suspend fun getRepository(repoId: String): RepoDetails {
        TODO("Not yet implemented")
    }

    override suspend fun getRepositoryReadme(
        ownerName: String,
        repositoryName: String,
        branchName: String
    ): String {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(token: String): UserInfo {
        gitApi.getUserInfo("token $token")
        keyValueStorage.authToken = token
        return keyValueStorage.authToken as UserInfo
    }
    override fun logout() {
        keyValueStorage.authToken = null
    }
}
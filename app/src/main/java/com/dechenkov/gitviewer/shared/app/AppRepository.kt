package com.dechenkov.gitviewer.shared.app

import android.util.Base64
import com.dechenkov.gitviewer.modules.list_repositories.domain.usecases.GetAuthHeaderUseCase
import com.dechenkov.gitviewer.shared.*
import com.dechenkov.gitviewer.shared.gitApi.IGitApi
import com.dechenkov.gitviewer.shared.language_colors.ILanguageColors
import com.dechenkov.gitviewer.shared.models.Repo
import com.dechenkov.gitviewer.shared.models.RepoDetails
import com.dechenkov.gitviewer.shared.models.UserInfo
import com.dechenkov.gitviewer.shared.value_storage.IKeyValueStorage

class AppRepository(
    private val gitApi: IGitApi,
    private val keyValueStorage: IKeyValueStorage,
    private val getAuthHeader: GetAuthHeaderUseCase,
    private val languageColors: ILanguageColors,
) : IAppRepository {
    override suspend fun getRepositories(): List<Repo> = requestWrapper {
        gitApi.getRepositories(getAuthHeader())
    }?.map { repoResponse ->
        repoResponse.toRepo().let { repo ->
            if (repo.language.isEmpty()) return@let repo
            repo.copy(color = languageColors.getColor(repo.language))
        }
    }!!

    override suspend fun getRepository(owner: String, repository: String): RepoDetails
    = requestWrapper {
        gitApi.getRepoDetails(
            token = getAuthHeader(),
            owner = owner,
            repo = repository
        )
    }?.toRepoDetails()!!

    override suspend fun getRepositoryReadme(
        ownerName: String,
        repositoryName: String,
        branchName: String?
    ): String {
        return requestWrapper {
            if (branchName.isNullOrEmpty())
                gitApi.getReadme(
                    token = getAuthHeader(),
                    owner = ownerName,
                    repo = repositoryName
                )
            else
                gitApi.getReadme(
                    token = getAuthHeader(),
                    owner = ownerName,
                    repo = repositoryName,
                    branch = branchName
                )
        }.let {
            Base64.decode(it!!.content, Base64.DEFAULT).decodeToString()
        }
    }

    override suspend fun getUrlImage(owner: String, repository: String, path: String): String = requestWrapper {
        gitApi.getContent(
            token = getAuthHeader(),
            owner = owner,
            repo = repository,
            path = path
        )
    }.toString()

    override suspend fun signIn(token: String): UserInfo = requestWrapper {
        gitApi.getUserInfo("token $token")
    }?.toUserInfo().also {
        keyValueStorage.authToken = token
    }!!

    override fun logout() {
        keyValueStorage.authToken = null
    }

}
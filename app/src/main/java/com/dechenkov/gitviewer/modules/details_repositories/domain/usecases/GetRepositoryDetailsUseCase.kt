package com.dechenkov.gitviewer.modules.details_repositories.domain.usecases

import com.dechenkov.gitviewer.shared.app.IAppRepository
import com.dechenkov.gitviewer.shared.models.RepoDetails
import javax.inject.Inject


class GetRepositoryDetailsUseCase
@Inject
constructor(
    private val appRepository: IAppRepository
) {
    suspend operator fun invoke(owner: String, repository: String): RepoDetails =
        appRepository.getRepository(owner, repository)
}
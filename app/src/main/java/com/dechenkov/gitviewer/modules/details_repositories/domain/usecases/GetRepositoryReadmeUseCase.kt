package com.dechenkov.gitviewer.modules.details_repositories.domain.usecases

import com.dechenkov.gitviewer.shared.app.IAppRepository
import javax.inject.Inject

class GetRepositoryReadmeUseCase
@Inject
constructor(
    private val appRepository: IAppRepository
) {
    suspend operator fun invoke(owner: String, repository: String) =
        appRepository.getRepositoryReadme(
            ownerName = owner,
            repositoryName = repository
        )
}
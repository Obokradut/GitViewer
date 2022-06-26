package com.dechenkov.gitviewer.modules.list_repositories.domain.usecases

import com.dechenkov.gitviewer.shared.app.IAppRepository
import com.dechenkov.gitviewer.shared.models.Repo
import javax.inject.Inject

class GetRepositoriesUseCase
@Inject
constructor(
    private val appRepository: IAppRepository
) {
    suspend operator fun invoke(): List<Repo>
        = appRepository.getRepositories()
}
package com.dechenkov.gitviewer.navigation.domain.usecases

import com.dechenkov.gitviewer.navigation.domain.IMainDestinations
import com.dechenkov.gitviewer.navigation.domain.MainNavProvider
import javax.inject.Inject

class NavigateToDetailsRepositoryUseCase
@Inject
constructor(
    private val mainNavProvider: MainNavProvider
) {
    operator fun invoke(repository: String, owner: String) {
        mainNavProvider.requestNavigationFlow(IMainDestinations.DetailsRepositories(repository,owner))
    }
}
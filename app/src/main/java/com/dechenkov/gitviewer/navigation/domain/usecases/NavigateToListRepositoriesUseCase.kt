package com.dechenkov.gitviewer.navigation.domain.usecases

import com.dechenkov.gitviewer.navigation.domain.IMainDestinations
import com.dechenkov.gitviewer.navigation.domain.MainNavProvider
import javax.inject.Inject

class NavigateToListRepositoriesUseCase
@Inject
constructor(
    private val navProvider: MainNavProvider
) {
    operator fun invoke() = navProvider.requestNavigationFlow(IMainDestinations.ListRepositories)
}
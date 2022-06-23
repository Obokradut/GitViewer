package com.dechenkov.gitviewer.navigation.domain.usecases

import com.dechenkov.gitviewer.modules.navigation.IMainDestinations
import com.dechenkov.gitviewer.modules.navigation.MainNavProvider
import javax.inject.Inject

class NavigateToListRepositories
@Inject
constructor(
    private val navProvider: MainNavProvider
) {
    operator fun invoke() = navProvider.requestNavigationFlow(IMainDestinations.ListRepositories)
}
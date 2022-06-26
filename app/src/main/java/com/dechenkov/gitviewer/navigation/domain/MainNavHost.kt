package com.dechenkov.gitviewer.navigation.domain

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.dechenkov.gitviewer.R
import com.dechenkov.gitviewer.modules.core.presentation.MainActivity
import com.dechenkov.gitviewer.modules.details_repositories.presentation.DetailInfoFragment
import kotlinx.coroutines.launch

fun MainActivity.launchNavHost(
    mainNavProvider: MainNavProvider,
    mainNavController: NavController
) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            mainNavProvider.observeNavigationFlow(this@repeatOnLifecycle) { destination ->
                when (destination) {
                    is IMainDestinations.Authorization -> mainNavController.navigate(R.id.navigateToAuth)
                    is IMainDestinations.ListRepositories -> mainNavController.navigate(R.id.navigateToRepositoriesList)
                    is IMainDestinations.DetailsRepositories ->
                        mainNavController.navigate(
                            R.id.navigateToDetailInfo,
                            DetailInfoFragment.createArguments(
                                destination.owner,
                                destination.repository
                            )
                        )
                    else -> {}
                }
            }
        }
    }
}

fun MainActivity.setupActionBarWithDestinations(
    coreNavController: NavController
) {
    coreNavController.addOnDestinationChangedListener { _, destination, _ ->
        if (destination.id == R.id.authorizationFragment) supportActionBar?.hide()
        else supportActionBar?.show()

        if (destination.id == R.id.repositoriesListFragment)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}
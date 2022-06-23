package com.dechenkov.gitviewer.modules.navigation

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.dechenkov.gitviewer.R
import com.dechenkov.gitviewer.modules.core.presentation.MainActivity
import kotlinx.coroutines.launch

fun MainActivity.launchNavHost(
    mainNavProvider: MainNavProvider,
    mainNavController: NavController
) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            mainNavProvider.observeNavigationFlow(this@repeatOnLifecycle) { destination ->
                when (destination) {
                    is IMainDestinations.Authorization -> TODO("навигация к фрагменту авторизации")
                    is IMainDestinations.ListRepositories -> TODO("навигация к фрагменту лист репозиториев")
                    is IMainDestinations.DetailsRepositories -> TODO("навигация к фрагменту детальная инфа о репозитории")
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
        if(destination.id == R.id.authorizationFragment) supportActionBar?.hide() else supportActionBar?.show()

    }
}
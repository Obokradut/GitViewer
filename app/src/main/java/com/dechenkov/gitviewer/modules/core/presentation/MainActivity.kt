package com.dechenkov.gitviewer.modules.core.presentation

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.dechenkov.gitviewer.R
import com.dechenkov.gitviewer.databinding.ActivityMainBinding
import com.dechenkov.gitviewer.modules.core.domain.CoreViewModel
import com.dechenkov.gitviewer.navigation.domain.launchNavHost
import com.dechenkov.gitviewer.navigation.domain.setupActionBarWithDestinations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private val coreViewModel: CoreViewModel by viewModels()

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                coreViewModel.coreAction.collect {
                    when (it) {
                        is CoreViewModel.Action.ShowDialog -> {
                            AlertDialog.Builder(this@MainActivity)
                                .setTitle(getString(R.string.logout))
                                .setMessage(getString(R.string.are_you_sure))
                                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                                    it.onSuccess()
                                }
                                .setNegativeButton(getString(R.string.no)) { _, _ ->

                                }
                                .create()
                                .show()
                        }
                    }
                }
            }
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.coreNavContainer) as NavHostFragment

        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        launchNavHost(coreViewModel.mainNavProvider, navController)


        setupActionBarWithNavController(
            this as AppCompatActivity,
            navController
        )
        setupActionBarWithDestinations(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_bar, menu)
        menu.findItem(R.id.logout_button)?.setOnMenuItemClickListener {
            coreViewModel.onLogoutClick()
            true
        }
        return super.onCreateOptionsMenu(menu)
    }
}
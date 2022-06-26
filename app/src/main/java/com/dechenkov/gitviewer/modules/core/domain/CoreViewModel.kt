package com.dechenkov.gitviewer.modules.core.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dechenkov.gitviewer.navigation.domain.IMainDestinations
import com.dechenkov.gitviewer.navigation.domain.MainNavProvider
import com.dechenkov.gitviewer.shared.app.IAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoreViewModel
@Inject
constructor(
    val mainNavProvider: MainNavProvider,
    private val appRepository: IAppRepository
): ViewModel() {
    private val _coreActions: Channel<Action> = Channel()
    val coreAction: Flow<Action>
        get() = _coreActions.receiveAsFlow()

    fun onLogoutClick() {
        viewModelScope.launch {
            _coreActions.send(
                Action.ShowDialog {
                    appRepository.logout()
                    mainNavProvider.requestNavigationFlow(IMainDestinations.Authorization)
                }
            )
        }
    }

    sealed interface Action {
        data class ShowDialog(val onSuccess: () -> Unit) : Action
    }
}
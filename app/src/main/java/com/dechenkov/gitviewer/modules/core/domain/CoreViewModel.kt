package com.dechenkov.gitviewer.modules.core.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dechenkov.gitviewer.modules.authorization.domain.AuthViewModel
import com.dechenkov.gitviewer.modules.navigation.MainNavProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CoreViewModel
@Inject
constructor(
    val mainNavProvider: MainNavProvider
): ViewModel() {
    private val _coreActions: Channel<Action> = Channel()
    val coreAction: Flow<Action>
        get() = _coreActions.receiveAsFlow()

    fun onLogoutClick() {
        viewModelScope.launch {
            _coreActions.send(
                Action.ShowDialog {
                    TODO("logout")
                }
            )
        }
    }

    sealed interface Action {
        data class ShowDialog(val onSuccess: () -> Unit) : Action
    }
}
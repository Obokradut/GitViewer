package com.dechenkov.gitviewer.modules.authorization.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dechenkov.gitviewer.modules.authorization.domain.usecases.*
import com.dechenkov.gitviewer.navigation.domain.usecases.NavigateToListRepositoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private val getGitToken: GetGitTokenUseCase,
    private val getEnterGitToken: GetEnterGitTokenUseCase,
    private val signIn: SignInUseCase,
    private val navigateToListRepositories: NavigateToListRepositoriesUseCase,
    private val connectionErrorMessage: GetConnectionErrorMessageUseCase,
    private val invalidInputMessage: GetInvalidInputMessageUseCase
) : ViewModel() {
    private val _state: MutableLiveData<State> = MutableLiveData(State.Loading)
    val state: LiveData<State>
        get() = _state

    private val _action: Channel<Action> = Channel(Channel.BUFFERED)
    val actions: Flow<Action>
        get() = _action.receiveAsFlow()

    val token: MutableLiveData<String> = MutableLiveData("")

    init {
        authorization()
    }

    fun onSingButtonPressed() {
        viewModelScope.launch {
            _state.postValue(State.Loading)
            try {
                if (token.value.isNullOrEmpty()) {
                    _state.postValue(State.InvalidInput(getEnterGitToken()))
                    return@launch
                }

                signIn(token.value!!)
                navigateToListRepositories()
            } catch (ex: Exception) {
                if (ex.message.toString().isEmpty()) {
                    _state.postValue(State.InvalidInput(requireNotNull(ex.message)))
                } else {
                    _state.postValue(State.InvalidInput(invalidInputMessage()))
                }
            }
        }
    }

    private fun authorization() {
        viewModelScope.launch {
            try {
                val gitToken = getGitToken()
                when (gitToken) {
                    null -> {
                        _state.postValue(State.Idle)
                        return@launch
                    }
                }
                signIn(gitToken!!)
                navigateToListRepositories()
            } catch (ex: Exception) {
                if (ex.message.toString().isEmpty()) {
                    _state.postValue(State.InvalidInput(requireNotNull(ex.message)))
                } else {
                    _state.postValue(State.InvalidInput(connectionErrorMessage()))
                }
            }
        }
    }

    sealed interface State {
        object Idle : State
        object Loading : State
        data class InvalidInput(val reason: String) : State
    }

    sealed interface Action {
        data class ShowError(val message: String) : Action
    }
}

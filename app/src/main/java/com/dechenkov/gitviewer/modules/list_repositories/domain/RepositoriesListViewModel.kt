package com.dechenkov.gitviewer.modules.list_repositories.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dechenkov.gitviewer.modules.authorization.domain.AuthViewModel
import com.dechenkov.gitviewer.modules.list_repositories.domain.usecases.GetRepositoriesUseCase
import com.dechenkov.gitviewer.navigation.domain.usecases.NavigateToDetailsRepositoryUseCase
import com.dechenkov.gitviewer.shared.models.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoriesListViewModel
@Inject
constructor(
    private val getRepositories: GetRepositoriesUseCase,
    private val navigateToDetailsRepository: NavigateToDetailsRepositoryUseCase
) : ViewModel() {
    private var _state: MutableLiveData<State> = MutableLiveData(State.Loading)
    val state: LiveData<State>
        get() = _state


    init {
        stateUpdate()
    }


    fun stateUpdate() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(State.Loading)
            try {
                getRepositories().let {
                    if (it.isNotEmpty())
                        _state.postValue(State.Loaded(repos = it))
                    else
                        _state.postValue(State.Empty)
                }
            }
            catch (ex: Exception) {
                if (ex.message.toString().isEmpty())
                    _state.postValue(State.Error(requireNotNull(ex.message)))
                else
                    _state.postValue(State.Error(ex.message.toString()))
            }
        }
    }

    fun onRepositoryClick(repository: String, owner: String)
        = navigateToDetailsRepository(repository, owner)

    sealed interface State {
        object Loading : State
        data class Loaded(val repos: List<Repo>) : State
        data class Error(val error: String) : State
        object Empty : State
        object ConnectionError : State
    }
}
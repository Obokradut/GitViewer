package com.dechenkov.gitviewer.modules.details_repositories.domain

import androidx.lifecycle.*
import com.dechenkov.gitviewer.modules.authorization.domain.AuthViewModel
import com.dechenkov.gitviewer.modules.details_repositories.domain.usecases.EditReadmeUseCase
import com.dechenkov.gitviewer.modules.details_repositories.domain.usecases.GetRepositoryDetailsUseCase
import com.dechenkov.gitviewer.modules.details_repositories.domain.usecases.GetRepositoryReadmeUseCase
import com.dechenkov.gitviewer.modules.details_repositories.domain.usecases.OpenUrlUseCase
import com.dechenkov.gitviewer.shared.models.RepoDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailInfoViewModel
@Inject
constructor(
    private val openUrl: OpenUrlUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val getRepositoryDetailsUseCase: GetRepositoryDetailsUseCase,
    private val getRepositoryReadme: GetRepositoryReadmeUseCase,
    private val replaceReadme: EditReadmeUseCase
) : ViewModel() {
    private val _state: MutableLiveData<State> = MutableLiveData(State.Loading)
    val state: LiveData<State>
        get() = _state

    private val repository = requireNotNull(
        savedStateHandle.get<String>(REPOSITORY_NAME)
    )
    private val owner = requireNotNull(
        savedStateHandle.get<String>(REPOSITORY_OWNER)
    )

    init {
        loadDetailRepoInfo(
            repository = repository,
            owner = owner
        )
    }

    private fun loadDetailRepoInfo(repository: String, owner: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(State.Loading)
            try {
                getRepositoryDetailsUseCase(owner, repository).let {
                    _state.postValue(
                        State.Loaded(
                            githubRepo = it,
                            readmeState = ReadmeState.Loading
                        )
                    )
                    loadRepositoryReadme(
                        State.Loaded(
                            githubRepo = it,
                            readmeState = ReadmeState.Loading
                        )
                    )
                }
            } catch (ex: Exception) {
                if(_state.value is State.Loading) {
                    _state.postValue(State.ConnectionError)
                } else {
                    _state.postValue(State.Error(ex.message.toString()))
                }
            }
        }
    }

    fun onUrlClick(url: String) = openUrl(url)

    private suspend fun loadRepositoryReadme(state: State.Loaded) {
        try {
            getRepositoryReadme(
                owner = state.githubRepo.owner,
                repository = state.githubRepo.name,
            ).let {
                replaceReadme(
                    owner = state.githubRepo.owner,
                    repository = state.githubRepo.name,
                    readme = it
                ).let { markdown ->
                    _state.postValue(
                        state.copy(
                            readmeState = ReadmeState.Loaded(markdown)
                        )
                    )
                }
            }
        } catch (ex: Exception) {
            _state.postValue(state.copy(readmeState = ReadmeState.Empty))
        }
    }

    fun stateUpdate() {
        loadDetailRepoInfo(repository,owner)
    }

    companion object NavConsts {
        const val REPOSITORY_OWNER = "repoOwnerKey"
        const val REPOSITORY_NAME = "repoNameKey"
    }

    sealed interface State {
        object Loading : State
        data class Error(val error: String) : State
        object ConnectionError : State
        data class Loaded(
            val githubRepo: RepoDetails,
            val readmeState: ReadmeState
        ) : State
    }

    sealed interface ReadmeState {
        object Loading : ReadmeState
        object Empty : ReadmeState
        data class Error(val error: String) : ReadmeState
        data class Loaded(val markdown: String) : ReadmeState
    }
}
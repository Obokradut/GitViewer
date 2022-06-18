package com.dechenkov.gitviewer.modules.authorization.domain

sealed interface AuthorizationViewState {
    object Loading: AuthorizationViewState
    object PresentInfo: AuthorizationViewState
    data class Error(val reason: String): AuthorizationViewState
}
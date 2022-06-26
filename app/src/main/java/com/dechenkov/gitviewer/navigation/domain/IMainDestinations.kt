package com.dechenkov.gitviewer.navigation.domain

sealed interface IMainDestinations {
    object Authorization : IMainDestinations
    object ListRepositories : IMainDestinations
    data class DetailsRepositories(val repository: String, val owner: String) : IMainDestinations
}
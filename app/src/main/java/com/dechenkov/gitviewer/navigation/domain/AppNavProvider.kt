package com.dechenkov.gitviewer.navigation.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

open class AppNavProvider<D> {
    private val navigationFlow: MutableStateFlow<D?> = MutableStateFlow(null)

    fun requestNavigationFlow(
        destinations: D?
    ) {
        navigationFlow.tryEmit(destinations)
    }

    fun observeNavigationFlow(
        scope: CoroutineScope,
        handler: (D?) -> Unit
    ) {
        navigationFlow.onEach {
            handler(it)
            if(it != null) navigationFlow.tryEmit(null)
        }.launchIn(scope)
    }
}
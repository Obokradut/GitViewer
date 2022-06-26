package com.dechenkov.gitviewer.modules.authorization.domain.usecases


import com.dechenkov.gitviewer.shared.value_storage.IKeyValueStorage
import javax.inject.Inject

class GetGitTokenUseCase
@Inject
constructor(
    private val keyValueStorage: IKeyValueStorage
) {
    operator fun invoke(): String? = keyValueStorage.authToken
}
package com.dechenkov.gitviewer.modules.list_repositories.domain.usecases

import com.dechenkov.gitviewer.shared.value_storage.IKeyValueStorage
import javax.inject.Inject

class GetAuthHeaderUseCase
@Inject
constructor(
    private val keyValueStorage: IKeyValueStorage
) {
    operator fun invoke() : String = "token ${keyValueStorage.authToken}"
}
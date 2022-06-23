package com.dechenkov.gitviewer.modules.authorization.domain.usecases

import com.dechenkov.gitviewer.shared.IAppRepository
import javax.inject.Inject

class SignInUseCase
@Inject
constructor(
  private val repository: IAppRepository
) {
    suspend operator fun invoke(token: String) = repository.signIn(token)
}
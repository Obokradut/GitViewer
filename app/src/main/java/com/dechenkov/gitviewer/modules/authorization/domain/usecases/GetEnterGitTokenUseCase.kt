package com.dechenkov.gitviewer.modules.authorization.domain.usecases

import android.content.Context
import com.dechenkov.gitviewer.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetEnterGitTokenUseCase
@Inject
constructor(
    @ApplicationContext
    private val context: Context
){
    operator fun invoke() : String
        = context.getString(R.string.enter_git_token)
}
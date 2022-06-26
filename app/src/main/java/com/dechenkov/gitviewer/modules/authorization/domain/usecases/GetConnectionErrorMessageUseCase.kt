package com.dechenkov.gitviewer.modules.authorization.domain.usecases

import android.content.Context
import com.dechenkov.gitviewer.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetConnectionErrorMessageUseCase
@Inject
constructor(
    @ApplicationContext
    private val context: Context
) {
    operator fun invoke() = context.getString(R.string.connection_error)
}
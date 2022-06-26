package com.dechenkov.gitviewer.modules.details_repositories.domain.usecases

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class OpenUrlUseCase
@Inject
constructor(
    @ApplicationContext
    private val context: Context
) {
    private val intent: Intent = Intent()
    operator fun invoke(url: String) {
        val uri = Uri.parse(url)
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK).apply {
            data = uri
            context.startActivity(this)
        }
    }
}
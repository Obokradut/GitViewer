package com.dechenkov.gitviewer.shared.models

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    val login: String
)

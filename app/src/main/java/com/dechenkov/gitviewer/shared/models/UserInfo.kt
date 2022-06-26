package com.dechenkov.gitviewer.shared.models

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo (
    val login: String
)
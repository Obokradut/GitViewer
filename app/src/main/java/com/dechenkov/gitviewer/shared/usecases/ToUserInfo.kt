package com.dechenkov.gitviewer.shared

import com.dechenkov.gitviewer.shared.models.UserInfo
import com.dechenkov.gitviewer.shared.models.UserInfoResponse

fun UserInfoResponse.toUserInfo() = UserInfo(login = this.login)
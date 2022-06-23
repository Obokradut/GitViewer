package com.dechenkov.gitviewer.shared

import android.content.Context

private const val STORAGE_KEY = "GIT_STORAGE"
private const val TOKEN_KEY = "GIT_TOKEN"

class KeyValueStorage(
    context: Context
) : IKeyValueStorage {
    private val storage = context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)

    override var authToken: String?
        get()
            = storage.getString(TOKEN_KEY, null)
        set(value)
            = storage.edit().putString(TOKEN_KEY, value).apply()
}
package com.dechenkov.gitviewer.shared.value_storage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.dechenkov.gitviewer.shared.value_storage.IKeyValueStorage

private const val STORAGE_KEY = "GIT_STORAGE"
private const val TOKEN_KEY = "GIT_TOKEN"

class KeyValueStorage(
    context: Context
) : IKeyValueStorage {
    private val storage = context.getSharedPreferences(STORAGE_KEY, MODE_PRIVATE)

    override var authToken: String?
        get()
            = storage.getString(TOKEN_KEY, null)
        set(value)
            = storage.edit().putString(TOKEN_KEY, value).apply()
}
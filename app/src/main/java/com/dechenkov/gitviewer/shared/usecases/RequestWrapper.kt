package com.dechenkov.gitviewer.shared

suspend fun <T> requestWrapper(request: suspend () -> T) : T? {
    return try {
        request()
    }
    catch (ex: Exception){
        return null
    }
}



package com.leoleo.androidgithubsearch.data.api

import android.util.Log
import com.leoleo.androidgithubsearch.data.BuildConfig
import io.ktor.client.plugins.logging.*

class AppHttpLogger : Logger {
    override fun log(message: String) {
        if (BuildConfig.DEBUG) {
            Log.w("AppHttpLogger", message)
        }
    }
}
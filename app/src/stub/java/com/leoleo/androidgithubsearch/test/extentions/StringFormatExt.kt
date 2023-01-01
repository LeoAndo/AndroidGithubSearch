package com.leoleo.androidgithubsearch.test.extentions

import android.content.Context
import kotlinx.serialization.StringFormat
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets

inline fun <reified T> StringFormat.decodeFromStubData(
    context: Context,
    format: Json,
    jsonFileName: String
): T {
    return try {
        val inputStream: InputStream = context.assets.open(jsonFileName)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val jsonString = String(buffer, StandardCharsets.UTF_8)
        val data = format.decodeFromString<T>(jsonString)
        data
    } catch (ex: IOException) {
        throw ex
    }
}
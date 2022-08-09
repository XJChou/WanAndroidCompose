package com.zxj.wanandroid.compose.data.datasource

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.zxj.wanandroid.compose.datastore.CookiesPreferences
import java.io.InputStream
import java.io.OutputStream

class CookiesPreferencesSerializer : Serializer<CookiesPreferences> {

    override val defaultValue: CookiesPreferences = CookiesPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): CookiesPreferences {
        try {
            return CookiesPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: CookiesPreferences, output: OutputStream) = t.writeTo(output)
}
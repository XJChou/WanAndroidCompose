package com.zxj.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.zxj.wanandroid.compose.datastore.CookiesPreferences
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class CookiesPreferencesSerializer @Inject constructor() : Serializer<CookiesPreferences> {

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
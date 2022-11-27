package com.zxj.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.zxj.datastore.CookiesPreferencesSerializer
import com.zxj.datastore.UserPreferencesSerializer
import com.zxj.wanandroid.compose.datastore.CookiesPreferences
import com.zxj.wanandroid.compose.datastore.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        userPreferencesSerializer: UserPreferencesSerializer
    ): DataStore<UserPreferences> =
        DataStoreFactory.create(serializer = userPreferencesSerializer) {
            context.dataStoreFile("user_preferences.pb")
        }

    @Provides
    @Singleton
    fun providesCookiesPreferencesDataStore(
        @ApplicationContext context: Context,
        userPreferencesSerializer: CookiesPreferencesSerializer
    ): DataStore<CookiesPreferences> =
        DataStoreFactory.create(serializer = userPreferencesSerializer) {
            context.dataStoreFile("cookies.pb")
        }
}
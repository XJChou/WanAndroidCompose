package com.zxj.database

import com.zxj.database.dao.HistorySearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    @Singleton
    fun providesAuthorDao(
        database: AppDatabase,
    ): HistorySearchDao = database.historySearchDao()

}

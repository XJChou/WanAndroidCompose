package com.zxj.wanandroid.compose.data.database

import android.content.Context
import androidx.room.*
import com.zxj.wanandroid.compose.data.bean.HistorySearchBean
import com.zxj.wanandroid.compose.data.bean.HotSearchBean
import com.zxj.wanandroid.compose.data.datasource.SearchLocalDataSource
import java.util.*


@Database(
    entities = [
        HistorySearchBean::class,
        HotSearchBean::class
    ],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object Instance {

        private lateinit var instance: AppDatabase

        fun init(context: Context) {
            val appContext = context.applicationContext
            instance = Room
                .databaseBuilder(appContext, AppDatabase::class.java, "database")
                .build()
        }

        operator fun invoke(): AppDatabase = instance
    }

    abstract fun searchDao(): SearchLocalDataSource

}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long): Date  = Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date): Long = date.time
}
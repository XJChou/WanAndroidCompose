package com.zxj.database

import androidx.room.*
import com.zxj.database.dao.HistorySearchDao
import com.zxj.database.model.HistorySearchEntity
import com.zxj.database.model.HotSearchEntity
import com.zxj.database.utils.Converters
import java.util.*

@Database(
    entities = [
        HistorySearchEntity::class,
        HotSearchEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historySearchDao(): HistorySearchDao
}

package org.dementiev.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.dementiev.data.entity.NewsItem
import org.dementiev.data.local.dao.NewsDao

@Database(entities = [NewsItem::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
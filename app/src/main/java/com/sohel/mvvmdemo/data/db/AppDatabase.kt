package com.sohel.mvvmdemo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sohel.mvvmdemo.data.db.dao.IssuesDao
import com.sohel.mvvmdemo.data.db.entity.IssueResponse


@Database(entities = [IssueResponse::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun issueDao(): IssuesDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "appDatabase.db"
            )
                .build()
    }
}
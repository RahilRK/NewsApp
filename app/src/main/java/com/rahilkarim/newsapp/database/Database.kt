package com.rahilkarim.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rahilkarim.newsapp.R
import com.rahilkarim.newsapp.models.Article
import com.rahilkarim.newsapp.util.Converters

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class Database: RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE: com.rahilkarim.newsapp.database.Database? = null

        fun getInstance(context: Context): com.rahilkarim.newsapp.database.Database {

            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = createDatabase(context)
                }
            }

            return INSTANCE!!
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            com.rahilkarim.newsapp.database.Database::class.java,
            context.resources.getString(R.string.app_name)
        ).build()
    }

    abstract fun contactdao(): ArticleDAO
}
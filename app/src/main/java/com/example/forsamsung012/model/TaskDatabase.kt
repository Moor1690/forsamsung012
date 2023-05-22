package com.example.forsamsung012.model

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskModel::class, TaskListName::class], version = 2/*, exportSchema = false*/)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDAO(): TaskDAO


    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null


        fun getDatabase(context: Context): TaskDatabase {
            Log.d("getDatabase", "getDatabase")
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java, "notify_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }


        /*fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "my_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }*/
    }
}
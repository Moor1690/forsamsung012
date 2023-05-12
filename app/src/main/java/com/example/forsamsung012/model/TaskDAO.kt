package com.example.forsamsung012.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDAO {
    @Query("SELECT * FROM my_table")
    fun getAllObjects(): LiveData<List<TaskModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertObject(taskModel: TaskModel)
}
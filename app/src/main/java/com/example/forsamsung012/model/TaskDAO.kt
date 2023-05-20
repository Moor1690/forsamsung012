package com.example.forsamsung012.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDAO {
    @Query("SELECT * FROM taskModel")
    fun getAllObjects(): LiveData<List<TaskModel>>
    @Query("SELECT taskModel.* FROM taskModel JOIN taskListName ON taskModel.taskListNameKey = taskListName.taskListNameId Where taskListName.name = :listName")//WHERE listName = :listName")
    fun getAllObjectsByListName(listName:String): LiveData<List<TaskModel>>

    /*@Query("SELECT * FROM my_table WHERE listName = :listName")
    fun getAllObjectsFromList(listName:String): LiveData<List<TaskModel>>*/
    @Query("SELECT DISTINCT name FROM taskListName")
    fun getAllListName(): LiveData<List<String>>

    @Query("SELECT * FROM taskModel WHERE name = :name AND task = :task")
    fun getObject(name: String, task: String): TaskModel
/*    @Query("SELECT * FROM my_table WHERE `key` = :key")
    fun getObjectByKey(key: Long): TaskModel*/
    @Query("SELECT * FROM taskModel WHERE key = :key")
    fun getObjectByKey(key: Long): TaskModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertObject(taskModel: TaskModel)

    @Update
    fun updateObject(taskModel: TaskModel)

    @Delete
    fun deleteObject(taskModel: TaskModel)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListName(taskListName:TaskListName)
    @Query("SELECT * FROM taskListName")
    fun getAllTaskListName(): LiveData<List<TaskListName>>

    @Query("SELECT * FROM taskListName WHERE name =:name")
    fun getAllListName(name:String): TaskListName
}
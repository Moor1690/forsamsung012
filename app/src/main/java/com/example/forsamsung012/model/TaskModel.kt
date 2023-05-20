package com.example.forsamsung012.model

import android.provider.ContactsContract.Data
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey


@Entity(tableName = "taskListName")
data class TaskListName(
    @PrimaryKey(autoGenerate = true)
    val taskListNameId: Long = 0,
    val name: String
)
@Entity(
    tableName = "taskModel",
    foreignKeys = [ForeignKey(
        entity = TaskListName::class,
        parentColumns = ["taskListNameId"],
        childColumns = ["taskListNameKey"],
        onDelete = CASCADE
    )]
)
data class TaskModel(
    @PrimaryKey(autoGenerate = true)
    val key: Long = 0,
    val taskListNameKey: Long,
    val listName: String,
    val name: String,
    val task: String,
    val toDelete: Boolean = false,/*,
    val dataLastchange: Data*/
)



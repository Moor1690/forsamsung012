package com.example.forsamsung012.model

import android.provider.ContactsContract.Data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_table")
data class TaskModel(
    @PrimaryKey(autoGenerate = true)
    val key: Long = 0,
    val listName: String,
    val name: String,
    val task: String,
    val toDelete: Boolean = false,/*,
    val dataLastchange: Data*/
)
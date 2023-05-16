package com.example.forsamsung012.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class test(
    key:Long,
    application: Application,
) {
    val taskDAO = TaskDatabase.getDatabase(context = application).taskDAO()

    suspend fun getObjectByKey(key: Long) :LiveData<TaskModel>{

        return liveData {taskDAO.getObjectByKey(key)}
    }

    val get: LiveData<TaskModel> = liveData {
        //withContext(Dispatchers.IO) {
        taskDAO.getObjectByKey(key)
    }//}
}
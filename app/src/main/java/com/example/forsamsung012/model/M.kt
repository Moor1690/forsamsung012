package com.example.forsamsung012.model

import androidx.lifecycle.LiveData

class M(private val taskDAO: TaskDAO)  {
    fun getAllObjects(): LiveData<List<TaskModel>> {
        return taskDAO.getAllObjects()
    }

    /*fun addProject(projectModel: TaskModel) {
        taskDAO.addProject(projectModel = projectModel)
    }*/

    fun insertObject(taskModel: TaskModel) {
        taskDAO.insertObject(taskModel = taskModel)
    }

    /*fun deleteProject(projectModel: TaskModel) {
        taskDAO.deleteProject(projectModel = projectModel)
    }*/
}
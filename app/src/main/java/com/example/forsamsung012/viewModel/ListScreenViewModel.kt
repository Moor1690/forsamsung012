package com.example.forsamsung012.viewModel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.forsamsung012.model.TaskDatabase
import com.example.forsamsung012.model.TaskListName
import com.example.forsamsung012.model.TaskModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListScreenViewModel(application: Application) :
    AndroidViewModel(application) {

    val taskDAO = TaskDatabase.getDatabase(context = application).taskDAO()

    var taskList: LiveData<List<TaskModel>> = foo()
    fun foo(): LiveData<List<TaskModel>> {
        return taskDAO.getAllTaskModelByListName("")
    }

    fun getFirstListNane(listName: MutableState<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            val li = taskDAO.getAllNameTaskListName().value
        }
    }

    fun getAllObjectsByListName(listName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            taskList = taskDAO.getAllTaskModelByListName(listName)
        }
    }


    fun deleteObject(taskModel: TaskModel) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDAO.deleteTaskModel(taskModel)
        }
    }

    fun getAllListName(): LiveData<List<String>> {
        return taskDAO.getAllNameTaskListName()
    }

    fun insertListName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDAO.insertListName(TaskListName(name = name))
        }
    }

    fun removeUserData(
        databaseReference2: DatabaseReference,
        userData: MutableState<List<TaskModel>>,
    ) {
        var auth: FirebaseAuth = FirebaseAuth.getInstance()
        val database =
            Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/")
        val databaseReference = database.getReference("0/${auth.uid}/TASK/")
        database.getReference("0/${auth.uid}/TASK/-NVAqmQ7jjnYTDEsC954").removeValue()

    }

}
package com.example.forsamsung012.viewModel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.forsamsung012.model.MyRepository
import com.example.forsamsung012.model.TaskDatabase
import com.example.forsamsung012.model.TaskListName
import com.example.forsamsung012.model.TaskModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalTime

class ListScreenViewModel(application: Application) :
    AndroidViewModel(application) {

    val taskDAO = TaskDatabase.getDatabase(context = application).taskDAO()

    var taskList: LiveData<List<TaskModel>> = foo()
    var taskListToDelete: List<TaskModel> = mutableListOf()//getAllObjectsByListNameToDelete("")
    var myR = MyRepository(application, FirebaseAuth.getInstance())

    fun getAllTaskListName():LiveData<List<TaskListName>>{
        return taskDAO.getAllTaskListName()
    }
    fun check(): LiveData<List<TaskModel>>{
        //viewModelScope.launch(Dispatchers.IO) {
        return taskDAO.getAllTaskModel()
        //}
    }
    fun check2(che: State<List<TaskModel>>, che2:State<List<TaskListName>>){
        viewModelScope.launch(Dispatchers.IO) {
            myR.check(che, che2)
        }
    }

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
/*    fun getAllObjectsByListNameToDelete(listName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("taskListToDelete", taskListToDelete.toString())
            taskListToDelete = taskDAO.getAllTaskModelByListName2(listName)
            Log.d("taskListToDelete", taskListToDelete.toString())
        }
    }*/


    fun deleteObject(taskModel: TaskModel) {
        viewModelScope.launch(Dispatchers.IO) {
            myR.removeUserData(taskModel.key)
            taskDAO.deleteTaskModel(taskModel)
        }
    }

    fun getAllListName(): LiveData<List<String>> {
        return taskDAO.getAllNameTaskListName()
    }

    fun insertListName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            myR.insertListName(TaskListName(name = name))
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


    fun deleteTaskListName(taskListName: TaskListName){
        viewModelScope.launch(Dispatchers.IO) {
            myR.deleteTaskListName(taskListName = taskListName, listToDelete = taskDAO.getAllTaskModelByListName2(taskListName.name))
        }
    }

}
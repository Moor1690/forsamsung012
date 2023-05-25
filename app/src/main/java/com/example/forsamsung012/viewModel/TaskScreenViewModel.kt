package com.example.forsamsung012.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.forsamsung012.model.MyRepository
import com.example.forsamsung012.model.TaskDatabase
import com.example.forsamsung012.model.TaskModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskScreenViewModel(
    application: Application,
    auth: FirebaseAuth
) : AndroidViewModel(application) {

    val taskDAO = TaskDatabase.getDatabase(context = application).taskDAO()
    val application2 = application
    //val database = Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/")
    //private val databaseReference = Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/").getReference("0/${FirebaseAuth.getInstance().uid}/TASK/")

    var myR = MyRepository(application, auth)
    fun insertObject(
        taskName: MutableState<String>,
        taskDescription: MutableState<String>,
        listName: MutableState<String>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("listNameSOUT", listName.value)
            var id = taskDAO.getAllNameTaskListName(listName.value).taskListNameId
            myR.insertObject(taskName, taskDescription, listName, id)

        }
        Toast.makeText(application2, "Data successful save.", Toast.LENGTH_SHORT)
            .show()
    }

    fun updateObject(
        taskName: MutableState<String>,
        taskDescription: MutableState<String>,
        listName: MutableState<String>,
        key: Long
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            myR.updateObject(
                TaskModel(
                    taskListNameKey = taskDAO.getAllNameTaskListName(listName.value).taskListNameId,
                    key = key,
                    listName = listName.value,
                    name = taskName.value,
                    task = taskDescription.value
                )
            )
        }
        Toast.makeText(application2, "Data successful update.", Toast.LENGTH_SHORT)
            .show()
    }


    fun getAllListName() {
        viewModelScope.launch(Dispatchers.IO) {
            myR.getAllListName()
        }
    }


    fun getObjectByKey(key: Long, message1: MutableState<String>, message2: MutableState<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            //delay(1000)
            val taskModel = myR.getObjectByKey(key)
            message1.value = taskModel.name
            message2.value = taskModel.task
        }

    }


}
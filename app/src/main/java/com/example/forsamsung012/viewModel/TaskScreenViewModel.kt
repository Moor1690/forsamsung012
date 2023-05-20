package com.example.forsamsung012.viewModel

import android.app.Application
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
    key:Long,
    application: Application,
    auth : FirebaseAuth
) : AndroidViewModel(application)  {

    val taskDAO = TaskDatabase.getDatabase(context = application).taskDAO()
    //val database = Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/")
    //private val databaseReference = Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/").getReference("0/${FirebaseAuth.getInstance().uid}/TASK/")

    var myR = MyRepository(key,application,auth)
    fun insertObject(taskModel: TaskModel, message1: MutableState<String>,  message2: MutableState<String>, listName: MutableState<String>){
        viewModelScope.launch(Dispatchers.IO) {
            myR.insertObject(taskModel,message1,message2,listName)
        }
    }
    fun updateObject(taskModel: TaskModel){
        viewModelScope.launch(Dispatchers.IO) {
            myR.updateObject(taskModel)
        }
    }


    fun getAllListName(){
        viewModelScope.launch(Dispatchers.IO) {
            myR.getAllListName()
        }
    }


    fun getObjectByKey(key: Long, message1: MutableState<String>,  message2: MutableState<String>){
        viewModelScope.launch(Dispatchers.IO) {
            //delay(1000)
            val taskModel = myR.getObjectByKey(key)
            message1.value = taskModel.name
            message2.value = taskModel.task
        }

    }




    /*fun ad(){
        viewModelScope.launch(Dispatchers.IO) {
            m.insertObject(taskModel)
        }
    }*/
    //m.insertObject(taskModel)
}
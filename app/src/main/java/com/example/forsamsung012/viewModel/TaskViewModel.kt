package com.example.forsamsung012.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.forsamsung012.model.TaskDatabase
import com.example.forsamsung012.model.TaskModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZonedDateTime

class TaskViewModel(
    application: Application,
) : AndroidViewModel(application)  {

    val taskDAO = TaskDatabase.getDatabase(context = application).taskDAO()
    //val database = Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/")
    //private val databaseReference = Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/").getReference("0/${FirebaseAuth.getInstance().uid}/TASK/")

    fun insertObject(taskModel: TaskModel, message1: MutableState<String>,  message2: MutableState<String>, listName: MutableState<String>){
        viewModelScope.launch(Dispatchers.IO) {
            var taskModel = TaskModel(
                listName = listName.value,
                name = message1.value,
                task = message2.value
            )
            Log.d("getOneObjekt",taskModel.toString())
            taskDAO.insertObject(taskModel)
            //Log.d("getOneObjekt", taskDAO.getObject(message1.value, message2.value).toString())
            val dateTime = LocalDateTime.now()


            val databaseReference = Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/").getReference("0/${FirebaseAuth.getInstance().uid}/TASK/"+taskDAO.getObject(message1.value, message2.value).key)
            databaseReference.setValue(
                TaskModel(
                    listName = "task",
                    key = taskDAO.getObject(message1.value, message2.value).key,
                    name = message1.value,
                    task = message2.value
                )
            )

            Log.d("tagData", dateTime.toString())
        }

    }
    fun updateObject(taskModel: TaskModel, message1: MutableState<String>,  message2: MutableState<String>, listName: MutableState<String>){
        viewModelScope.launch(Dispatchers.IO) {
            taskDAO.updateObject(taskModel)
        }
    }


    fun getObjectByKey(key: Long): MutableLiveData<TaskModel>{
        val resultLiveData: MutableLiveData<TaskModel> = MutableLiveData()
        viewModelScope.launch(Dispatchers.IO) {
            resultLiveData.postValue(taskDAO.getObjectByKey(key))
        }
        var i = 1000000
        while (i > 0){
            Log.d("tagWhile", i.toString())
            i--
            Log.d("resultLiveData.value", resultLiveData.value.toString())
        }
        Log.d("resultLiveData.value", resultLiveData.value.toString())
        return resultLiveData
    }




    /*fun ad(){
        viewModelScope.launch(Dispatchers.IO) {
            m.insertObject(taskModel)
        }
    }*/
    //m.insertObject(taskModel)
}
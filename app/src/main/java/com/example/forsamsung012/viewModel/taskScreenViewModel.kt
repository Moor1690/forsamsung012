package com.example.forsamsung012.viewModel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.forsamsung012.model.TaskDatabase
import com.example.forsamsung012.model.TaskModel
import com.example.forsamsung012.model.test
import com.example.forsamsung012.utils.State
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class taskScreenViewModel(
    key:Long,
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
    fun updateObject(taskModel: TaskModel){
        viewModelScope.launch(Dispatchers.IO) {
            taskDAO.updateObject(taskModel)
        }
    }

    /*fun getObject(name: String, task: String): TaskModel{

    }*/


    fun getObjectByKey(key: Long, message1: MutableState<String>,  message2: MutableState<String>){
        viewModelScope.launch(Dispatchers.IO) {
            //delay(1000)
            val taskModel = taskDAO.getObjectByKey(key)
            message1.value = taskModel.name
            message2.value = taskModel.task
        }

    }
    val stateTaskModel: LiveData<State<TaskModel>> = liveData {
        withContext(Dispatchers.IO) {
            try {
                emit(State.Success(taskDAO.getObjectByKey(key)))
            } catch (e: Exception) {
                //emit(State.Error<List<TaskModel>>(e))
            }
        }
    }




    /*fun ad(){
        viewModelScope.launch(Dispatchers.IO) {
            m.insertObject(taskModel)
        }
    }*/
    //m.insertObject(taskModel)
}
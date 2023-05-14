package com.example.forsamsung012.viewModel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.forsamsung012.model.TaskModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.example.forsamsung012.model.TaskDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListScreenViewModel(application: Application, navController: NavHostController) : AndroidViewModel(application) {

    val taskDAO = TaskDatabase.getDatabase(context = application).taskDAO()
    private val readAllProjects: LiveData<List<TaskModel>> = taskDAO.getAllObjects()

    fun insertObject(taskModel: TaskModel){
        viewModelScope.launch(Dispatchers.IO) {
            taskDAO.insertObject(taskModel)
        }
    }

    fun getAllObjects(): LiveData<List<TaskModel>>{
        return readAllProjects
    }

    fun deleteObject(taskModel: TaskModel){
        viewModelScope.launch(Dispatchers.IO) {
            taskDAO.deleteObject(taskModel)
        }
    }
    fun goToTaskSkreen(taskModel: TaskModel){

    }

    fun getAllListName(): LiveData<List<String>> {
        return taskDAO.getAllListName()
    }


    fun removeUserData(
        databaseReference2: DatabaseReference,
        userData: MutableState<List<TaskModel>>,
    ) {
        var auth: FirebaseAuth = FirebaseAuth.getInstance()
        val database = Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/")
        val databaseReference = database.getReference("0/${auth.uid}/TASK/")
        database.getReference("0/${auth.uid}/TASK/-NVAqmQ7jjnYTDEsC954").removeValue()

    }

}
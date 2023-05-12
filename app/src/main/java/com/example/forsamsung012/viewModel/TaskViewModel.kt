package com.example.forsamsung012.viewModel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.forsamsung012.model.M
import com.example.forsamsung012.model.TaskDatabase
import com.example.forsamsung012.model.TaskModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(
    application: Application,
    context: Context,
    message1: MutableState<String>,
    message2: MutableState<String>
) : AndroidViewModel(application)  {



    var taskModel = TaskModel(
        key = 10,
        name = message1.value,
        task = message2.value,
        hash = "")
    val taskDAO = TaskDatabase.getDatabase(context = context).taskDAO()
    val m = M(taskDAO)
    fun ad(){
        viewModelScope.launch(Dispatchers.IO) {
            m.insertObject(taskModel)
        }
    }
    //m.insertObject(taskModel)
}
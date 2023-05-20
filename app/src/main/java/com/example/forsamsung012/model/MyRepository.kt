package com.example.forsamsung012.model

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MyRepository(
    key:Long,
    application: Application,
    auth : FirebaseAuth
) {
    val taskDAO = TaskDatabase.getDatabase(context = application).taskDAO()
    val database =
        Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/")
    private val databaseReference = database.getReference("0/${auth.uid}/TASK/")

    suspend fun insertObject(
        taskModel: TaskModel,
        message1: MutableState<String>,
        message2: MutableState<String>,
        listName: MutableState<String>
    ) {

        Log.d("getOneObjekt", taskModel.toString())
        //taskDAO.insertListName(TaskListName(name = listName.value))

        var taskModel = TaskModel(
            taskListNameKey = taskDAO.getAllListName(listName.value).taskListNameId,
            listName = listName.value,
            name = message1.value,
            task = message2.value
        )
        taskDAO.insertObject(taskModel)

        Log.d("qwertyyuiop", taskDAO.getAllTaskListName().value.toString())
        //Log.d("qwertyyuiop", taskDAO.getAllObjects().value.toString())

        //Log.d("getOneObjekt", taskDAO.getObject(message1.value, message2.value).toString())
        val dateTime = LocalDateTime.now()


        val databaseReference =
            Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference(
                    "0/${FirebaseAuth.getInstance().uid}/TASK/" + taskDAO.getObject(
                        message1.value,
                        message2.value
                    ).key
                )
        databaseReference.setValue(taskModel)

        Log.d("tagData", dateTime.toString())

    }


    suspend fun updateObject(taskModel: TaskModel) {
        taskDAO.updateObject(taskModel)

        val databaseReference =
            Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("0/${FirebaseAuth.getInstance().uid}/TASK/" + taskModel.key)
        databaseReference.setValue(taskModel)
    }

    suspend fun getObjectByKey(
        key: Long
    ) : TaskModel{

        val databaseReference =
            Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("0/${FirebaseAuth.getInstance().uid}/TASK/" + key)
        //Log.d("databaseReference.get()",databaseReference.get)


        return taskDAO.getObjectByKey(key)
    }

    suspend fun getAllListName(): LiveData<List<String>>{
        return taskDAO.getAllListName()
    }
}
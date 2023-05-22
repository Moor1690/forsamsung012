package com.example.forsamsung012.model

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

class MyRepository(
    application: Application,
    auth : FirebaseAuth
) {
    val taskDAO = TaskDatabase.getDatabase(context = application).taskDAO()
    val database =
        Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/")
    private val databaseReference = database.getReference("0/${auth.uid}/TASK/")

    suspend fun insertObject(
        taskName: MutableState<String>,
        taskDescription: MutableState<String>,
        listName: MutableState<String>,
        id:Long
    ) {

        Log.d("listNameSOUT",listName.value)
        //taskDAO.insertListName(TaskListName(name = listName.value))

        var taskModel = TaskModel(
            taskListNameKey = id,
            listName = listName.value,
            name = taskName.value,
            task = taskDescription.value
        )
        taskDAO.insertTaskModel(taskModel)

        Log.d("qwertyyuiop", taskDAO.getAllTaskListName().value.toString())
        //Log.d("qwertyyuiop", taskDAO.getAllObjects().value.toString())

        //Log.d("getOneObjekt", taskDAO.getObject(message1.value, message2.value).toString())
        val dateTime = LocalDateTime.now()


        val databaseReference =
            Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference(
                    "0/${FirebaseAuth.getInstance().uid}/TASK/" + taskDAO.getTaskModel(
                        taskName.value,
                        taskDescription.value
                    ).key
                )
        databaseReference.setValue(taskModel)

        Log.d("tagData", dateTime.toString())

    }


    suspend fun updateObject(taskModel: TaskModel) {
        taskDAO.updateTaskModel(taskModel)

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


        return taskDAO.getTaskModelByKey(key)
    }

    suspend fun getAllListName(): LiveData<List<String>>{
        return taskDAO.getTaskListNameByName()
    }
}
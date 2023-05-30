package com.example.forsamsung012.model

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

import java.time.LocalDateTime
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MyRepository(
    application: Application,
    auth: FirebaseAuth
) {
    val taskDAO = TaskDatabase.getDatabase(context = application).taskDAO()
    val database =
        Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/")
    private val databaseReference = database.getReference("0/${auth.uid}/TASK/")

    suspend fun insertObject(
        taskName: MutableState<String>,
        taskDescription: MutableState<String>,
        listName: MutableState<String>,
        id: Long
    ) {

        Log.d("listNameSOUT", listName.value)
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
        databaseReference.setValue(taskDAO.getTaskModel(
            taskName.value,
            taskDescription.value
        ))

        Log.d("tagData", dateTime.toString())

    }

    suspend fun insertListName(taskListName: TaskListName){
        taskDAO.insertListName(taskListName)
        var l = taskDAO.getAllNameTaskListName(taskListName.name)//.taskListNameId
        Log.d("l.id1",taskDAO.getAllNameTaskListName(taskListName.name).taskListNameId.toString())
        Log.d("l.id", l.taskListNameId.toString())
        Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference(
                "0/${FirebaseAuth.getInstance().uid}/TASKLISTNAME/" + l.taskListNameId//taskDAO.getAllNameTaskListName(taskListName.name).taskListNameId
            ).setValue(l)
        //databaseReference
    }


    suspend fun updateObject(taskModel: TaskModel) {
        taskDAO.updateTaskModel(taskModel)
        Log.d("updateObject", taskModel.toString())
        val databaseReference =
            Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("0/${FirebaseAuth.getInstance().uid}/TASK/" + taskModel.key)
        databaseReference.setValue(taskModel)
    }

    suspend fun getObjectByKey(
        key: Long
    ): TaskModel {

        val databaseReference =
            Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("0/${FirebaseAuth.getInstance().uid}/TASK/" + key)


        return taskDAO.getTaskModelByKey(key)
    }

    suspend fun getAllListName(): LiveData<List<String>> {
        return taskDAO.getAllNameTaskListName()
    }


    suspend fun removeUserData(key:Long){
        val database :FirebaseDatabase = Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/")
        val databaseReference = database.getReference("0/${FirebaseAuth.getInstance().uid}/TASK/")
        database.getReference("0/${FirebaseAuth.getInstance().uid}/TASK/" + key.toString()).removeValue()
    }

    suspend fun check(dataFromRoom: List<TaskModel>, nameTaskListFromRoom: List<TaskListName>){


        var dataFromFirebase : MutableState<List<TaskModel>> = mutableStateOf<List<TaskModel>>(listOf())
        var nameTaskListFromFirebase: List<TaskListName> = getAllTaskListName()
        Log.d("fromFB", getAllTaskListName().toString())
        Log.d("insVal", nameTaskListFromFirebase.toString())

        val databaseReference = database.getReference("0/${FirebaseAuth.getInstance().uid}/TASK/")
        dataFromFirebase = getUserData(databaseReference, dataFromFirebase)

        Log.d("nameTaskListFromFirebase.size", nameTaskListFromFirebase.size.toString())
        Log.d("nameTaskListFromRoom.size", nameTaskListFromRoom.size.toString())

        if(!isEmptyFirebase() && nameTaskListFromRoom.isNotEmpty()){
            Log.d("not", "empty1234")
            if(nameTaskListFromFirebase.size < nameTaskListFromRoom.size) {
                Log.d("FromFirebase.toString", nameTaskListFromFirebase.toString())
                for (value in nameTaskListFromRoom) {
                    setListNameFromRoom(nameTaskListFromRoom)
                    setTaskFromRoom(dataFromRoom)
                }
            } else{
                Log.d("more inFirebase", nameTaskListFromFirebase.toString())
                setListNameFromFirebase(nameTaskListFromFirebase)
                setTaskFromFirebase(dataFromFirebase)
            }
        } else if (!isEmptyFirebase() && nameTaskListFromRoom.isEmpty()){
            setListNameFromFirebase(nameTaskListFromFirebase)
            setTaskFromFirebase(dataFromFirebase)
        } else if(isEmptyFirebase() && nameTaskListFromRoom.isNotEmpty()){
            setListNameFromRoom(nameTaskListFromRoom)
            setTaskFromRoom(dataFromRoom)
        }



    }

    private fun setTaskFromRoom(dataFromRoom: List<TaskModel>){
        for (value in dataFromRoom){
            Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/").getReference("0/${FirebaseAuth.getInstance().uid}/TASK/" + value.key).setValue(value)
        }
    }
    private fun setListNameFromRoom(nameTaskListFromRoom: List<TaskListName>){
        for (value in nameTaskListFromRoom){
            Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/").getReference("0/${FirebaseAuth.getInstance().uid}/TASKLISTNAME/" + value.taskListNameId).setValue(value)
        }
    }
    private fun setTaskFromFirebase(dataFromFirebase: MutableState<List<TaskModel>>){
        for (value in dataFromFirebase.value){
            taskDAO.insertTaskModel(value)
        }
    }
    private fun setListNameFromFirebase(nameTaskListFromFirebase: List<TaskListName>){
        for (value in nameTaskListFromFirebase){
            taskDAO.insertListName(value)
        }
    }

    private suspend fun isEmptyFirebase() : Boolean = suspendCoroutine { continuation ->
        val databaseReference = Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/").getReference("0/${FirebaseAuth.getInstance().uid}/" )
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Записи существуют по указанному пути
                    continuation.resume(false)
                    Log.d("TAG123", "Записи существуют по указанному пути.")
                } else {
                    continuation.resume(true)
                    // Записи не существуют по указанному пути
                    Log.d("TAG123", "Записи не существуют по указанному пути.")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG123", "Ошибка при чтении данных.", error.toException())
            }
        })
    }

    private suspend fun getAllTaskListName() : List<TaskListName> = suspendCoroutine { continuation ->
        val databaseReference = Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/").getReference("0/${FirebaseAuth.getInstance().uid}/TASKLISTNAME/")
        //Log.d("TAG", "getUserData")
        databaseReference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userList = mutableListOf<TaskListName>()
                    for (ds in dataSnapshot.children) {
                        val userMap = ds.value as HashMap<*, *>
                        val userModel = TaskListName(
                            userMap["taskListNameId"] as Long,
                            userMap["name"] as String,
                        )
                        userList.add(userModel)
                    }
                    continuation.resume(userList.toList())

                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            }
        )

    }

    fun getUserData(
        databaseReference: DatabaseReference,
        userData: MutableState<List<TaskModel>>,
    ) : MutableState<List<TaskModel>>{
        //Log.d("TAG", "getUserData")
        databaseReference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userList = mutableListOf<TaskModel>()
                    for (ds in dataSnapshot.children) {
                        val userMap = ds.value as HashMap<*, *>
                        val userModel = TaskModel(
                            userMap["key"] as Long,
                            userMap["taskListNameKey"] as Long,
                            userMap["listName"] as String,
                            userMap["name"] as String,
                            userMap["task"] as String/*,
                            userMap["hash"] as String*/
                        )
                        userList.add(userModel)
                    }
                    //Log.d("TAG", "successfully to read value."
                    Log.d("TAG", "successfully to read value.")
                    userData.value = userList
                    Log.d("TAG", userData.value.toString())
                    //Log.d("TAG", userData.value[1].toString())
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            }
        )

        return userData
    }

    suspend fun deleteTaskListName(taskListName: TaskListName, listToDelete: List<TaskModel>){
        val databaseReference2 = Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/").getReference("0/${FirebaseAuth.getInstance().uid}/TASKLISTNAME/" + taskListName.taskListNameId)
        databaseReference2.removeValue()
        for (value in listToDelete){
            val databaseReference3 = Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/").getReference("0/${FirebaseAuth.getInstance().uid}/TASK/" + value.key)
            databaseReference3.removeValue()
        }
        taskDAO.deleteTaskListName(taskListName)
    }
}
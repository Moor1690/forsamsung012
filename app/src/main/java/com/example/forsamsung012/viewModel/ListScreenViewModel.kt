package com.example.forsamsung012.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.forsamsung012.model.M
import com.example.forsamsung012.model.TaskModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.example.forsamsung012.model.TaskDatabase

class ListScreenViewModel(application: Application) : AndroidViewModel(application) {

    val taskDAO = TaskDatabase.getDatabase(context = application).taskDAO()
    val m = M(taskDAO)
    private val readAllProjects: LiveData<List<TaskModel>> = m.getAllObjects()
    fun getUserData2() :LiveData<List<TaskModel>>{
        return readAllProjects
    }

    fun getAllObjects(): LiveData<List<TaskModel>>{
        return readAllProjects
    }


    fun getUserData(
        databaseReference: DatabaseReference,
        userData: MutableState<List<TaskModel>>,
    ) {
        //Log.d("TAG", "getUserData")
        databaseReference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userList = mutableListOf<TaskModel>()
                    for (ds in dataSnapshot.children) {
                        Log.d("TAG", "фывйцуыывds:\t"+ ds.toString())
                        val userMap = ds.value as HashMap<*, *>
                        val userModel = TaskModel(
                            userMap["key"] as Long,
                            userMap["name"] as String,
                            userMap["task"] as String,
                            userMap["hash"] as String
                        )
                        userList.add(userModel)
                    }
                    //Log.d("TAG", "successfully to read value."
                    Log.d("TAG", "successfully to read value.")

                    userData.value = userList
                    Log.d("TAG", databaseReference.key.toString())
                    Log.d("TAG", userData.value.toString())
                    //Log.d("TAG", userData.value[1].toString())

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            }
        )
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
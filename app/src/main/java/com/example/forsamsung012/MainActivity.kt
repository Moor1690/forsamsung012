package com.example.forsamsung012

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.forsamsung012.model.TaskModel
import com.example.forsamsung012.navigate.Navigate
import com.example.forsamsung012.ui.theme.Forsamsung012Theme
import com.example.forsamsung012.viewModel.ListScreenViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.storage.ktx.storage

class MainActivity : ComponentActivity() {

    val userData = mutableStateOf<List<TaskModel>>(listOf()) //this is list of user tasks

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    val database = Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/")


    private lateinit var listScreenViewModel: ListScreenViewModel


    public var cUser = auth.currentUser
    private val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
    private val databaseReference = database.getReference("0/${auth.uid}/TASK/")

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //Forsamsung012Theme {
                val provider = ViewModelProvider(this)
                listScreenViewModel = provider[ListScreenViewModel::class.java]
                var userData2 = listScreenViewModel.getAllObjects().observeAsState(initial = listOf())

                val navController: NavHostController = rememberAnimatedNavController()
                val isShowBottomBar = remember { mutableStateOf(false) }
                Scaffold(
                    modifier = Modifier.fillMaxSize()//,
                ) {

                    Navigate(
                        userData = userData2,
                        database = database,
                        cUser = cUser,
                        auth = auth,
                        navController = navController,
                        context = this,
                        isShowBottomBar = isShowBottomBar,
                        firebaseRemoteConfig = firebaseRemoteConfig,
                        databaseReference = databaseReference
                    )
                }
            //}
        }
    }
}


/*
public fun getUserData(
    databaseReference: DatabaseReference,
    userData: MutableState<List<TaskModel>>,
) {
    //Log.d("TAG", "getUserData")
    databaseReference.addValueEventListener(
        object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userList = mutableListOf<TaskModel>()
                for (ds in dataSnapshot.children) {
                    val userMap = ds.value as HashMap<*, *>
                    val userModel = TaskModel(
                        userMap["key"] as Int,
                        userMap["name"] as String,
                        userMap["task"] as String,
                        userMap["hash"] as String
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
}*/

package com.example.forsamsung012

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.forsamsung012.model.TaskModel
import com.example.forsamsung012.navigate.Navigate
import com.example.forsamsung012.ui.theme.Forsamsung012Theme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.storage.ktx.storage

class MainActivity : ComponentActivity() {
    //gs://forsamsung012.appspot.com/USERS

    //private val db = "https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/"
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    //var storage = Firebase.storage("gs://forsamsung012.appspot.com/")
    val database = Firebase.database("https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/")

    public var cUser = auth.currentUser
    private val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
    //var storageRef = storage.reference
    private val databaseReference = FirebaseDatabase.getInstance().getReference("USERS/${auth.uid}")

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //auth.hashCode()
        val myRef = database.getReference("0/${auth.uid}/TASK2/")

        //Log.d("mytag1", "https://forsamsung012-default-rtdb.europe-west1.firebasedatabase.app/")
        //myRef.push().setValue("sdlflhjaksbdkhasbd")
        /*myRef.setValue(TaskModel(
            key = "testadd",
            name = "testadd",
            task = "testadd"
        ))*/
        /*databaseReference.push().setValue(
            TaskModel(
                key = databaseReference.push().key,
                name = "dadasdasfksadb",
                task = "phoneNum;l;fjlbnajksdrhnlwenehtcbgljewrhbtjhcerwrjkcgh,kjndshfcdslue"
            )
        )*/
        setContent {
            Forsamsung012Theme {
                val navController: NavHostController = rememberAnimatedNavController()
                val isShowBottomBar = remember { mutableStateOf(false) }
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize()//,
                    //color = MaterialTheme.colorScheme.background
                ) {

                    Navigate(
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
            }
        }
    }
}
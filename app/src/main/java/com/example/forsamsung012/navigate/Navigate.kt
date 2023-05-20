package com.example.forsamsung012.navigate

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import com.example.forsamsung012.screens.ListScreen
import com.example.forsamsung012.screens.SigInScreen
import com.example.forsamsung012.screens.TaskScreen
import com.example.forsamsung012.ui.theme.Forsamsung012Theme
import com.example.forsamsung012.viewModel.ListScreenViewModel
import com.example.forsamsung012.viewModel.TaskScreenViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig




@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigate(
    taskName: MutableState<String>,
    taskDescription: MutableState<String>,
    taskScreenViewModel: TaskScreenViewModel,
    listScreenViewModel:ListScreenViewModel,
    application: Application,
    database: FirebaseDatabase,
    auth : FirebaseAuth,
    navController: NavHostController,
    databaseReference: DatabaseReference,
    firebaseRemoteConfig: FirebaseRemoteConfig
){
    Forsamsung012Theme{
        var start: String
        if (auth.currentUser == null){
            start = "SigInScreen"
        } else {
            start = "ListScreen"
        }



        AnimatedNavHost(
            navController = navController,
            startDestination = start,///*"ListScreen"*/"SigInScreen",
            builder = {

                composable(route = "SigInScreen") {
                        SigInScreen(
                            auth = auth,
                            navController = navController,
                            context = application)
                        //isShowBottomBar.value = false
                }
/*                composable(route = "SignUpScreen") {
                    ForSamsung012Theme {
                        SignUpScreen(navController = navController, context)
                        isShowBottomBar.value = true
                    }
                }*/
                composable(route = "ListScreen") {
                    Log.d("TAG", "route = \"ListScreen\"")
                        ListScreen(
                            application = application,
                            listScreenViewModel = listScreenViewModel,
                            auth = auth,
                            navController = navController)
                    }
                composable(route = "TaskScreen/{taskModelId}"){ backStackEntry ->
                    TaskScreen(
                        taskName = taskName,
                        taskDescription = taskDescription,
                        taskModelId = backStackEntry.arguments?.getString("taskModelId"),
                        navController = navController,
                        taskScreenViewModel = taskScreenViewModel,
                        application = application,
                        auth = auth,
                        firebaseRemoteConfig = firebaseRemoteConfig,
                        databaseReference = databaseReference
                    )
                }
                composable(route = "TaskScreen"){
                    TaskScreen(
                        taskName = mutableStateOf(""),
                        taskDescription = mutableStateOf(""),
                        taskModelId = "",
                        navController = navController,
                        taskScreenViewModel = taskScreenViewModel,
                        application = application,
                        auth = auth,
                        firebaseRemoteConfig = firebaseRemoteConfig,
                        databaseReference = databaseReference
                    )
                }
            }
        )
    }
}



package com.example.forsamsung012.navigate

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import com.example.forsamsung012.model.TaskModel
import com.example.forsamsung012.screens.ListScreen
import com.example.forsamsung012.screens.SigInScreen
import com.example.forsamsung012.screens.TaskScreen
import com.example.forsamsung012.ui.theme.Forsamsung012Theme
import com.example.forsamsung012.viewModel.ListScreenViewModel
import com.example.forsamsung012.viewModel.TaskViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig




@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigate(
    taskViewModel: TaskViewModel,
    listScreenViewModel:ListScreenViewModel,
    application: Application,
    database: FirebaseDatabase,
    auth : FirebaseAuth,
    navController: NavHostController,
    isShowBottomBar: MutableState<Boolean>,
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
                        isShowBottomBar.value = false
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
                        isShowBottomBar.value = false
                    }
                composable(route = "TaskScreen/{taskModelId}"){ backStackEntry ->
                    TaskScreen(
                        taskModelId = backStackEntry.arguments?.getString("taskModelId"),
                        navController = navController,
                        taskViewModel = taskViewModel,
                        application = application,
                        database = database,
                        auth = auth,
                        firebaseRemoteConfig = firebaseRemoteConfig,
                        databaseReference = databaseReference
                    )
                    isShowBottomBar.value = true
                }
            }
        )
    }
}



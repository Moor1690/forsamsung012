package com.example.forsamsung012.navigate

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
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


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigate(
    listName: MutableState<String>,
    taskName: MutableState<String>,
    taskDescription: MutableState<String>,
    taskScreenViewModel: TaskScreenViewModel,
    listScreenViewModel: ListScreenViewModel,
    application: Application,
    auth: FirebaseAuth,
    navController: NavHostController,
    context: Context

) {
    Forsamsung012Theme {
        var start: String
        if (auth.currentUser == null) {
            start = "SigInScreen"
        } else {
            start = "ListScreen"
        }
        AnimatedNavHost(
            navController = navController,
            startDestination = start,
            builder = {
                composable(route = "SigInScreen") {
                    SigInScreen(
                        auth = auth,
                        navController = navController,
                        context = application
                    )
                }
                composable(route = "ListScreen") {
                    ListScreen(
                        listName = listName,
                        application = application,
                        listScreenViewModel = listScreenViewModel,
                        auth = auth,
                        navController = navController
                    )
                }
                composable(route = "TaskScreen/{taskModelId}") { backStackEntry ->
                    TaskScreen(
                        listName = listName,
                        taskName = taskName,
                        taskDescription = taskDescription,
                        taskModelId = backStackEntry.arguments?.getString("taskModelId"),
                        taskScreenViewModel = taskScreenViewModel,
                        navController = navController,
                        context = context
                    )
                }
                composable(route = "TaskScreen") {
                    TaskScreen(
                        listName = listName,
                        taskName = mutableStateOf(""),
                        taskDescription = mutableStateOf(""),
                        taskModelId = "",
                        taskScreenViewModel = taskScreenViewModel,
                        navController = navController,
                        context = context
                    )
                }
            }
        )
    }
}



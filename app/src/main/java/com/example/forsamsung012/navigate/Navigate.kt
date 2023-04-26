package com.example.forsamsung012.navigate

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import com.example.forsamsung012.screens.ListScreen
import com.example.forsamsung012.screens.SigInScreen
import com.example.forsamsung012.screens.TaskScreen
import com.example.forsamsung012.ui.theme.Forsamsung012Theme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigate(navController: NavHostController,
             context: Context,
             isShowBottomBar: MutableState<Boolean>
){
    Forsamsung012Theme{
        AnimatedNavHost(
            navController = navController,
            startDestination = "ListScreen"/*"SigInScreen"*/,
            builder = {
                composable(route = "SigInScreen") {
                        SigInScreen(navController = navController, context = context)
                        isShowBottomBar.value = false
                }
/*                composable(route = "SignUpScreen") {
                    ForSamsung012Theme {
                        SignUpScreen(navController = navController, context)
                        isShowBottomBar.value = true
                    }
                }*/
                composable(route = "ListScreen") {
                        ListScreen(navController = navController, context = context)
                        isShowBottomBar.value = false
                    }
                composable(route = "TaskScreen") {
                    TaskScreen(navController = navController, context = context)
                    isShowBottomBar.value = true
                }
            })
    }
}

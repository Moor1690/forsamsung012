package com.example.forsamsung012.navigate

import android.content.Context
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import com.example.forsamsung012.getUserData
import com.example.forsamsung012.model.TaskModel
import com.example.forsamsung012.screens.ListScreen
import com.example.forsamsung012.screens.SigInScreen
import com.example.forsamsung012.screens.TaskScreen
import com.example.forsamsung012.ui.theme.Forsamsung012Theme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigate(cUser: FirebaseUser?,
             userData: MutableState<List<TaskModel>>,
             database: FirebaseDatabase,
             auth : FirebaseAuth,
             navController: NavHostController,
             context: Context,
             isShowBottomBar: MutableState<Boolean>,
             databaseReference: DatabaseReference,
             firebaseRemoteConfig: FirebaseRemoteConfig
){
    Forsamsung012Theme{
        var start: String
        if (cUser == null){
            start = "SigInScreen"
        } else {
            start = "ListScreen"
        }


        AnimatedNavHost(
            navController = navController,
            startDestination = start,///*"ListScreen"*/"SigInScreen",
            builder = {
                composable(route = "SigInScreen") {
                        SigInScreen(auth = auth, cUser = cUser,navController = navController, context = context)
                        isShowBottomBar.value = false
                }
/*                composable(route = "SignUpScreen") {
                    ForSamsung012Theme {
                        SignUpScreen(navController = navController, context)
                        isShowBottomBar.value = true
                    }
                }*/
                composable(route = "ListScreen") {
                    Log.d("qwertytag", cUser.toString())
                    Log.d("TAG", "route = \"ListScreen\"")
                    getUserData(
                        databaseReference = databaseReference,
                        userData = userData
                    )
                        ListScreen(databaseReference = databaseReference,
                            userData = userData,auth = auth, cUser = auth.currentUser!!, navController = navController, context = context)
                        isShowBottomBar.value = false
                    }
                composable(route = "TaskScreen") {
                    TaskScreen(database = database, auth = auth, navController = navController, context = context, firebaseRemoteConfig = firebaseRemoteConfig, databaseReference = databaseReference)
                    isShowBottomBar.value = true
                }
            })
    }
}


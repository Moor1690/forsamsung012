package com.example.forsamsung012.screens

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.forsamsung012.R
import com.example.forsamsung012.bar.MinFabItem
import com.example.forsamsung012.bar.MultiFloatingButton
import com.example.forsamsung012.bar.MultiFloatingState
import com.example.forsamsung012.model.TaskDatabase
import com.example.forsamsung012.model.TaskModel
import com.example.forsamsung012.viewModel.TaskViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.inject.Deferred
import com.google.firebase.remoteconfig.FirebaseRemoteConfig



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    taskModelId: String?,
    taskViewModel: TaskViewModel,
    application: Application,
    database: FirebaseDatabase,
    auth: FirebaseAuth,
    databaseReference: DatabaseReference,
    firebaseRemoteConfig: FirebaseRemoteConfig,
    navController: NavHostController
) {



    val taskDAO = TaskDatabase.getDatabase(context = application).taskDAO()


    var multiFloatingState by remember {
        mutableStateOf(MultiFloatingState.Collapsed)
    }
    var listName = remember { mutableStateOf("ЗаМетка") }
    var message1 = remember { mutableStateOf("") }
    var message2 = remember { mutableStateOf("") }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()


    if (taskModelId != "-1"){
        var taskModel: MutableLiveData<TaskModel> = taskViewModel.getObjectByKey(taskModelId!!.toLong())
        //private val _data = MutableLiveData<TaskModel>()
        message1.value = taskModel.value!!.name
        message2.value = taskModel.value!!.task
        //Log.d("NAKANECTO!!!!", taskModelId)

    }
    val items = listOf(
        MinFabItem(
            icon = ImageBitmap.imageResource(id = R.drawable.delete),
            label = "Menu",
            identifier = "MenuFab"
        ),
        MinFabItem(
            icon = ImageBitmap.imageResource(id = R.drawable.delete),
            label = "Delete",
            identifier = "DeleteFab"
        )/*,
        MinFabItem(
            icon = Icons.Default.Menu,
            label = "Menu",
            identifier = "MenuFab"
        )*/
    )

    Scaffold(
        drawerBackgroundColor = SnackbarDefaults.backgroundColor,
        backgroundColor = SnackbarDefaults.backgroundColor,
        floatingActionButton = {
            MultiFloatingButton(
                listName = listName,
                taskViewModel = taskViewModel,
                context = application,
                database = database,
                auth = auth,
                multiFloatingState = multiFloatingState,
                onMultiFabStateChange = {
                    multiFloatingState = it
                },
                items = items,
                firebaseRemoteConfig = firebaseRemoteConfig,
                databaseReference = databaseReference,
                message1 = message1,
                message2 = message2
            )

        },
        scaffoldState = scaffoldState,
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
    ){
       /* Card(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)) {
            Text(text = "label", color = Color.White)
        }*/
        Column(){
            TextField(value = message1.value,
                modifier = Modifier.fillMaxHeight(0.1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor= Color.Green, // цвет при получении фокуса
                    unfocusedBorderColor = Color.LightGray),
                /*colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor= Color.Green, // цвет при получении фокуса
                    unfocusedBorderColor = Color.LightGray  // цвет при отсутствии фокуса
                ),*/
                onValueChange = { message1.value = it },
                textStyle = TextStyle(color = Color.White),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                label = { Text(text = "Назваине",
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()) })


            TextField(value = message2.value,
                onValueChange = { message2.value = it },
                modifier = Modifier.fillMaxSize(),
                textStyle = TextStyle(color = Color.White),
                label = { Text(text = "Задача",
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()) })
        }
    }





    /*Outlined*/
    //Text(text = "This is task Screen", color = Color.White)

}



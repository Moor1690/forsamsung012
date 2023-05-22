package com.example.forsamsung012.screens

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.forsamsung012.R
import com.example.forsamsung012.model.TaskDatabase
import com.example.forsamsung012.model.TaskModel
import com.example.forsamsung012.viewModel.TaskScreenViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.remoteconfig.FirebaseRemoteConfig



@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    listName: MutableState<String>,
    taskName: MutableState<String>,
    taskDescription: MutableState<String>,
    taskModelId: String?,
    taskScreenViewModel: TaskScreenViewModel,
    application: Application,
    auth: FirebaseAuth,
    databaseReference: DatabaseReference,
    firebaseRemoteConfig: FirebaseRemoteConfig,
    navController: NavHostController
) {


    var isChage = false

    val taskDAO = TaskDatabase.getDatabase(context = application).taskDAO()



    //var listName = remember { mutableStateOf("") }
    Log.d("listNameOnTaskScreen", listName.value)
    var message1 = taskName
    var message2 = taskDescription
    var key = Long//remember { mutableStateOf(Long) }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()




    Log.d("taskModelId", taskModelId.toString())
    if (taskModelId != ""){
        taskScreenViewModel.getObjectByKey(taskModelId!!.toLong(),message1,message2)
    }


    Scaffold(
        drawerBackgroundColor = SnackbarDefaults.backgroundColor,
        backgroundColor = SnackbarDefaults.backgroundColor,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                      if(taskModelId != ""){
                        taskScreenViewModel.updateObject(
                            taskName = taskName,
                            taskDescription = taskDescription,
                            listName = listName,
                            key = taskModelId!!.toLong()
                        )
                    }else{
                          taskScreenViewModel.insertObject(
                              taskName,
                              taskDescription,
                              listName
                        )
                    }
                }, modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp)
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Добавить",
                    modifier = Modifier.size(30.dp)
                )
            }

        },
        /*floatingActionButton = {
            MultiFloatingButton(
                key = taskModelId,
                listName = listName,
                taskScreenViewModel = taskScreenViewModel,
                context = application,
                //auth = auth,
                multiFloatingState = multiFloatingState,
                onMultiFabStateChange = {
                    multiFloatingState = it
                },
                items = items,
                //firebaseRemoteConfig = firebaseRemoteConfig,
                //databaseReference = databaseReference,
                taskName = message1,
                taskDescription = message2,
                isChange = isChage
            )

        },*/
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

            val focusManager = LocalFocusManager.current
            //val keyboardController = LocalSoftwareKeyboardController.current
            TextField(
                value = message2.value,
                onValueChange = { message2.value = it },
                modifier = Modifier.fillMaxSize(),
                textStyle = TextStyle(color = Color.White),
                label = { Text(text = "Задача",
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {focusManager.clearFocus()/*keyboardController?.hide()*/})
                /*,onImeActionPerformed = {
                    Log.d("sad","sda")
                }*/
            )
        }
    }





    /*Outlined*/
    //Text(text = "This is task Screen", color = Color.White)

}



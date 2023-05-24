package com.example.forsamsung012.screens

import android.annotation.SuppressLint
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.forsamsung012.viewModel.TaskScreenViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    listName: MutableState<String>,
    taskName: MutableState<String>,
    taskDescription: MutableState<String>,
    taskModelId: String?,
    taskScreenViewModel: TaskScreenViewModel,
    navController: NavHostController
) {
    val scaffoldState = rememberScaffoldState()


    if (taskModelId != "") {
        taskScreenViewModel.getObjectByKey(taskModelId!!.toLong(), taskName, taskDescription)
    }
    Scaffold(
        drawerBackgroundColor = SnackbarDefaults.backgroundColor,
        backgroundColor = SnackbarDefaults.backgroundColor,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (taskModelId != "") {
                        taskScreenViewModel.updateObject(
                            taskName = taskName,
                            taskDescription = taskDescription,
                            listName = listName,
                            key = taskModelId.toLong()
                        )
                    } else {
                        taskScreenViewModel.insertObject(
                            taskName,
                            taskDescription,
                            listName
                        )
                    }
                    navController.navigate("ListScreen")
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

        scaffoldState = scaffoldState,
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
    ) {
        Column {
            TextField(value = taskName.value,
                modifier = Modifier.fillMaxHeight(0.1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.DarkGray,
                    unfocusedBorderColor = Color.DarkGray
                ),
                onValueChange = { taskName.value = it },
                textStyle = TextStyle(color = Color.White),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                label = {
                    Text(
                        text = "Назваине",
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    )
                })

            val focusManager = LocalFocusManager.current
            TextField(
                value = taskDescription.value,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.DarkGray,
                    unfocusedBorderColor = Color.DarkGray
                ),
                onValueChange = { taskDescription.value = it },
                modifier = Modifier.fillMaxSize(),
                textStyle = TextStyle(color = Color.White),
                label = {
                    Text(
                        text = "Задача",
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )
        }
    }
}

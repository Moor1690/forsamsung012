package com.example.forsamsung012.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.forsamsung012.viewModel.TaskScreenViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun TaskScreen(
    listName: MutableState<String>,
    taskName: MutableState<String>,
    taskDescription: MutableState<String>,
    taskModelId: String?,
    taskScreenViewModel: TaskScreenViewModel,
    navController: NavHostController,
    context: Context
) {
    val scaffoldState = rememberScaffoldState()
    val calendar = Calendar.getInstance()
    var selectedDate = mutableStateOf("")
    val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    if (taskModelId != "") {
        taskScreenViewModel.getObjectByKey(taskModelId!!.toLong(), taskName, taskDescription)
    }
    Scaffold(
        drawerBackgroundColor = SnackbarDefaults.backgroundColor,
        backgroundColor = SnackbarDefaults.backgroundColor,
        floatingActionButton = {
            Column(
                //modifier = Modifier.height(200.dp)
            ) {
                Box(modifier = Modifier.padding(bottom = 16.dp)) {
                    FloatingActionButton(
                        onClick = {
                            DatePickerDialog(
                                context,
                                { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                                    calendar.set(mYear, mMonth, mDayOfMonth)
                                    selectedDate.value = dateFormat.format(calendar.time).toString()
                                    //newNotificationViewModel.setDate(calendar = calendar)
                                },
                                calendar[Calendar.YEAR],
                                calendar[Calendar.MONTH],
                                calendar[Calendar.DAY_OF_MONTH]
                            ).show()


                        }, modifier = Modifier
                            .clip(CircleShape)
                            .size(50.dp)//.padding(bottom = 16.dp)

                    ) {
                        Icon(
                            Icons.Filled.DateRange,
                            contentDescription = "Добавить",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }


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
                        .size(50.dp)//.padding(top = 16.dp)
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Добавить",
                        modifier = Modifier.size(30.dp)
                    )
                }

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

package com.example.forsamsung012.screens


import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.example.forsamsung012.MainActivity
import com.example.forsamsung012.bar.MyAppBar
import com.example.forsamsung012.model.TaskListName
import com.example.forsamsung012.viewModel.ListScreenViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalTime
import kotlin.system.exitProcess

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter",
    "UnrememberedMutableState"
)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ListScreen(
    listName: MutableState<String>,
    application: Application,
    listScreenViewModel: ListScreenViewModel,
    auth: FirebaseAuth,
    navController: NavHostController
) {
    val showDialog = remember { mutableStateOf(false) }
    val newListName = remember { mutableStateOf("") }


    var taskList = listScreenViewModel.taskList.observeAsState(initial = listOf())
    Log.d("taskList", taskList.value.toString())
    Log.d("LocalTime", LocalTime.now().toString())
    var listToDelete = listScreenViewModel.taskListToDelete

    var listNameList: State<List<TaskListName>> =
        listScreenViewModel.getAllTaskListName().observeAsState(initial = listOf())

    if ((listName.value == "") && (!listNameList.value.isEmpty())) {
        listName.value = listNameList.value[0].name
    }
    if(listNameList.value.isEmpty()){
        listName.value = ""
    }
    listScreenViewModel.getAllObjectsByListName(listName.value)


    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    var che = listScreenViewModel.check().observeAsState(initial = listOf())
    var nameTaskListFromRoom = listScreenViewModel.getAllTaskListName().observeAsState(initial = listOf())
    listScreenViewModel.check2(che,nameTaskListFromRoom)

    ExitOnBackPressed()

    //val context = LocalContext.current
    //val lifecycleOwner = LocalLifecycleOwner.current


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("TaskScreen") }, modifier = Modifier
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
        modifier = Modifier.background(color = Color.Red),
        scaffoldState = scaffoldState,
        drawerBackgroundColor = backgroundColor,
        backgroundColor = backgroundColor,
        drawerContentColor = Color.White,
        topBar = {
            MyAppBar(listName = listName,
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                })
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    androidx.compose.material3.Text(text = "${auth.currentUser!!.email}")
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Log out",
                        modifier = Modifier.clickable {
                            auth.signOut()
                            val intent = Intent(application, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            application.startActivity(intent)
                        })
                }
                LazyColumn {
                    items(listNameList.value) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(60.dp)
                                .padding(8.dp)
                                .clickable {
                                    listName.value = item.name
                                }
                        ) {
                            Row {
                                Text(
                                    text = item.name,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(start = 18.dp, top = 8.dp).weight(1f)
                                )
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "delete list",
                                    modifier = Modifier.padding(end = 18.dp, top = 8.dp)
                                        .clickable {
                                            //listScreenViewModel.getAllObjectsByListNameToDelete(item.name)
                                            Log.d("listToDelete",listToDelete.toString())
                                            listScreenViewModel.deleteTaskListName(item)
                                            //listName.value = item
                                        }
                                )
                            }

                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(60.dp)
                        .padding(8.dp)
                        .clickable {
                            showDialog.value = true
                        },
                ) {
                    Text(
                        text = "Создать новый лист задач",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 18.dp, top = 8.dp),
                        textAlign = TextAlign.Center
                    )

                }
            }

        }
    ) {

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                text = {
                    TextField(
                        value = newListName.value,
                        onValueChange = { newListName.value = it },
                        label = { androidx.compose.material3.Text("Название нового списка") }
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newListName.value != "") {
                                listScreenViewModel.insertListName(newListName.value)
                                showDialog.value = false
                            } else {
                                Toast.makeText(
                                    application,
                                    "You cannot create a list without a name.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    ) {
                        androidx.compose.material3.Text("Сохранить")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialog.value = false }
                    ) {
                        androidx.compose.material3.Text("Отмена")
                    }
                }
            )
        }

        var isSwipeRemoved by remember {
            mutableStateOf(false)
        }

        LazyColumn {

            items(taskList.value, key = { it.hashCode() }) { item ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToEnd) {
                            Log.d("deleteObject(item)", item.toString())
                            listScreenViewModel.deleteObject(item)
                            isSwipeRemoved = true
                        }
                        true
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(
                        DismissDirection.StartToEnd
                    ),
                    dismissThresholds = { FractionalThreshold(0.5f) },
                    background = {
                        val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                        val color by animateColorAsState(
                            targetValue = when (dismissState.targetValue) {
                                DismissValue.Default -> backgroundColor
                                DismissValue.DismissedToEnd -> backgroundColor
                                DismissValue.DismissedToStart -> backgroundColor
                            }
                        )

                        val icon = Icons.Default.Delete

                        val scale by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f)

                        val alignment = Alignment.CenterStart
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(12.dp),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                icon,
                                contentDescription = "icon",
                                modifier = Modifier.scale(scale),
                                tint = Color.Black
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                icon,
                                contentDescription = "icon",
                                modifier = Modifier.scale(scale)
                            )
                        }
                    },
                    dismissContent = {
                        androidx.compose.material.Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(110.dp)
                                .padding(8.dp),
                            backgroundColor = backgroundColor,
                            elevation = animateDpAsState(targetValue = if (dismissState.dismissDirection != null) 4.dp else 0.dp).value
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(110.dp)
                                    .padding(8.dp)
                                    .clickable {
                                        navController.navigate("TaskScreen/${item.key}")
                                    }
                            ) {
                                Text(
                                    text = item.name,
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                                )
                                Text(
                                    text = item.task,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp),
                                    maxLines = 2
                                )


                                /*BackHandler {
                                    navController.navigate("SigInScreen")
                                }*/
                            }
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun ExitOnBackPressed(backPressedOnce:MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current


    BackHandler {
        if (!backPressedOnce.value) {

            lifecycleOwner.lifecycleScope.launch {
                Toast.makeText(context, "Press again to exit", Toast.LENGTH_SHORT).show()
                backPressedOnce.value = true
                delay(2000)
                backPressedOnce.value = false

            }
        } else {exitProcess(0)}
    }
}

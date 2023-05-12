package com.example.forsamsung012.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.lazy.*
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.forsamsung012.bar.MyAppBar
import kotlinx.coroutines.launch


import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.forsamsung012.MainActivity
import com.example.forsamsung012.model.M
import com.example.forsamsung012.model.TaskDatabase
import com.example.forsamsung012.model.TaskModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter",
    "UnrememberedMutableState"
)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ListScreen(
    databaseReference: DatabaseReference,
    userData: State<List<TaskModel>>/*MutableState<List<TaskModel>>*/,
    auth: FirebaseAuth,
    cUser: FirebaseUser,
    navController: NavHostController,
    context: Context) {


    val taskDAO = TaskDatabase.getDatabase(context = context).taskDAO()
    val m = M(taskDAO)

    var userData2 = m.getAllObjects().observeAsState(initial = listOf())


    //доделать ЭТО!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    /*val listScreenViewModel = ListScreenViewModel(
        application = ViewModelProvider()[ListScreenViewModel::class.java],
        context = context)
    val userData = mutableStateOf<List<TaskModel>>(listOf())

    listScreenViewModel.getUserData(databaseReference, userData)*/

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            Button(onClick = { navController.navigate("TaskScreen")},modifier = Modifier
                .clip(CircleShape)
                .size(50.dp)) {
                Icon(Icons.Filled.Add, contentDescription = "Добавить", modifier = Modifier.size(50.dp))
            }
            /*FloatingActionButton(onClick = { navController.navigate("TaskScreen") }) {
                *//* FAB content *//*
                Icon(Icons.Filled.Add, contentDescription = "Добавить")
            }*/
        },
        modifier = Modifier.background(color = Color.Red),
        scaffoldState = scaffoldState,
        drawerBackgroundColor = backgroundColor,
        backgroundColor = backgroundColor,
        contentColor = Color.Red,
        drawerContentColor = Color.Red,
        topBar = {
            MyAppBar(onNavigationIconClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            })
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            Column() {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    androidx.compose.material3.Text(text = "${cUser.email}")
                    //Text(text = "$logged ${cUser.email}")
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Log out",
                        modifier = Modifier.clickable {
                            auth.signOut()
                            //cUser = auth.currentUser
                            context.startActivity(Intent(context, MainActivity::class.java))
                        })
                }
                Text(text = "text1")
                Text(text = "text2")
                Text(text = "text3")
            }

        }
    ) {


        var isSwipeRemoved by remember{
            mutableStateOf(false)
        }

        LazyColumn {

            items(userData2.value, key = { it.hashCode()}) { item ->
                //val currentItem by rememberUpdatedState(item)
                val dismissState = rememberDismissState(//rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToEnd){
                            //listScreenViewModel.removeUserData(databaseReference,userData)
                            //item.key!!.toInt()
                            //userData.value[item.key!!.toInt()]
                            //itemsList.remove(item)
                            //Log.d("MYdelete", userData.value.toString())
                            //Log.d("MYdelete", userData.value[item.key!!.toInt()].toString())
                            isSwipeRemoved = true
                        }
                        else if (it == DismissValue.DismissedToStart){
                            //listScreenViewModel.removeUserData(databaseReference,userData)
                            //userData.value[item.key!!.toInt()]
                            //Log.d("MYdelete", userData.value.toString())
                            //itemsList.remove(item)
                            isSwipeRemoved = true
                        }
                        true
                    }
                )





                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.StartToEnd,
                        DismissDirection.EndToStart),
                        dismissThresholds = {FractionalThreshold(0.5f)},
                        background = {
                            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                            val color by animateColorAsState(targetValue = when(dismissState.targetValue){
                                DismissValue.Default -> Color.LightGray
                                DismissValue.DismissedToEnd -> Color.Green
                                DismissValue.DismissedToStart -> Color.Red
                            })

                            val icon = when(direction){
                                DismissDirection.EndToStart -> Icons.Default.Done
                                DismissDirection.StartToEnd -> Icons.Default.Delete
                            }

                            val scale by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f)

                            val alignment = when(direction){
                                DismissDirection.EndToStart -> Alignment.CenterEnd
                                DismissDirection.StartToEnd -> Alignment.CenterStart
                            }

                            Box(modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(12.dp),
                                contentAlignment = alignment
                            ){
                                Icon(icon, contentDescription = "icon", modifier = Modifier.scale(scale), tint = Color.Black)
                            }
                            Box(modifier = Modifier
                                .fillMaxSize()
                                //.background(color)
                                .padding(12.dp),
                                contentAlignment = alignment) {
                                Icon(icon, contentDescription = "icon", modifier = Modifier.scale(scale))
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
                                //Text(text = item.name, fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
                                //Text(text =  item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name,  color = Color.White, modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp), maxLines = 2)

                                Card(

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(110.dp)
                                        .padding(8.dp)
                                ) {
                                    Text(text = item.name, fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
                                    Text(text =  item.task,  color = Color.White, modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp), maxLines = 2)
                                }
                            }
                            

                        }
                )



            }
        }



    }

}

private fun dpToPX(context: Context, dpValue: Float):Float{
    return dpValue * context.resources.displayMetrics.density
}

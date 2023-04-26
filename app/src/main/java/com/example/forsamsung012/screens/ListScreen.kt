package com.example.forsamsung012.screens

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.content.Context
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.forsamsung012.bar.MyAppBar
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ListScreen(navController: NavHostController, context: Context) {

    data class User(var name: String = "", var age: Int = 0){
    }

    val password = remember { mutableStateListOf("") }
    //Text(text = "List Screen")
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val person1 = User("John")
    val person2 = User("Johan2")
    person1.age = 10
    person2.age = 20

    var itemsList = remember {
        mutableStateListOf<User>()
    }
    var itemsList2 = mutableListOf<String>()
    itemsList2.add("asdasd")
    itemsList2.add("qe")
    itemsList2.add("asasadsddasd")
    itemsList2.add("asdqweasd")
    itemsList2.add("asdasdxazasd")
    itemsList2.add("asdaasdqg2334sd")
    itemsList2.add("as12335dasd")
    itemsList2.add("asdasd")
    itemsList2.add("qe")
    itemsList2.add("asasadsddasd")
    itemsList2.add("asdqweasd")
    itemsList2.add("asdasdxazasd")
    itemsList2.add("asdaasdqg2334sd")
    itemsList2.add("as12335dasd")
    itemsList.add(person1)
    itemsList.add(person2)
    itemsList.add(person1)
    itemsList.add(person2)
    itemsList.add(person1)
    itemsList.add(person2)
    itemsList.add(person1)
    itemsList.add(person2)
    itemsList.add(person1)
    itemsList.add(person2)
    itemsList.add(person1)
    itemsList.add(person2)

    //itemsList2[1].

    Scaffold(
        floatingActionButton = {
            Button(onClick = { navController.navigate("TaskScreen")},modifier = Modifier.clip(CircleShape).size(50.dp)) {
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
            Text(text = "text1")
            Text(text = "text2")
            Text(text = "text3")
        }
    ) {


        var isSwipeRemoved by remember{
            mutableStateOf(false)
        }

        LazyColumn {
            items(itemsList) { item ->
                //val currentItem by rememberUpdatedState(item)
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToEnd){
                            itemsList.remove(item)
                            isSwipeRemoved = true
                        }
                        else if (it == DismissValue.DismissedToStart){
                            itemsList.remove(item)
                            isSwipeRemoved = true
                        }
                        true
                    }
                )

                SwipeToDismiss(state = dismissState,
                    directions = setOf(DismissDirection.StartToEnd,
                        DismissDirection.EndToStart),
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
                                //.background(color)
                                .padding(12.dp),
                                contentAlignment = alignment) {
                                Icon(icon, contentDescription = "icon", modifier = Modifier.scale(scale))
                            }
                        },
                        dismissContent = {
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(110.dp)
                                .padding(8.dp)) {
                                Text(text = item.name, fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
                                Text(text =  item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name + item.name,  color = Color.White, modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp), maxLines = 2)
                            }
                        }
                )



            }
        }


        /*LazyColumn{
            items(items = itemsList){
                item ->
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(10.dp))
                        .padding(8.dp)
                        //.background(Color.Red)
                        .fillMaxWidth()
                        .heightIn(80.dp)
                        .swipeable(
                            state = swipeableState,
                            anchors = mapOf(
                                0f to 0,
                                -dpToPX(context = context, dpValue = 100f) to 1,
                                dpToPX(context = context, dpValue = 100f) to 2
                            ),
                            thresholds = { _, _ -> FractionalThreshold(0.3f) },
                            orientation = Orientation.Horizontal
                        )
                ) {
                    Button(onClick = { *//*TODO*//* }) {
                    Text(text = "btn")
                }
                Box(modifier = Modifier
                    .offset {
                        IntOffset(swipeableState.offset.value.roundToInt(), 0)
                    }
                    //.clip(shape = RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .heightIn(80.dp)
                    .background(Color.Green)
                ){
                    //Text(text = item.toString())
                    Text(text = item.name)
                    //Text(text = "mytext$it")
                }
            }
        }
    }*/

    }

}

private fun dpToPX(context: Context, dpValue: Float):Float{
    return dpValue * context.resources.displayMetrics.density
}

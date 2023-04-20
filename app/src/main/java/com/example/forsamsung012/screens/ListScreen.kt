package com.example.forsamsung012.screens

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ListScreen(navController: NavHostController, context: Context) {
    val password = remember { mutableStateOf("") }
    //Text(text = "List Screen")
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val swipeableState = rememberSwipeableState(initialValue = 0)


    Scaffold(
        Modifier.background(color = Color.Red),
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
    LazyColumn{
        items(15){
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(10.dp))
                    .padding(8.dp)
                    .background(Color.Red)
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
                Button(onClick = { /*TODO*/ }) {
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
                )
            }
        }
    }
    /*Column{
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(10.dp))
                    .padding(8.dp)
                    .background(Color.Red)
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
                )
            }
        }*/


    }

    /*Box(
            modifier = Modifier
                .background(Color.Red)
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
        Box(modifier = Modifier
            .offset {
                IntOffset(swipeableState.offset.value.roundToInt(), 0)
            }
            .fillMaxWidth()
            .heightIn(80.dp)
            .background(Color.Green)
        )
    }*/

}

private fun dpToPX(context: Context, dpValue: Float):Float{
    return dpValue * context.resources.displayMetrics.density
}

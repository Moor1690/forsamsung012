package com.example.forsamsung012.screens

import android.annotation.SuppressLint
import android.content.ClipData.Item
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.forsamsung012.bar.MyAppBar
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavHostController) {
    val password = remember { mutableStateOf("") }
    //Text(text = "List Screen")
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()


    Scaffold(
        Modifier.background(color = Color.Red),
        scaffoldState = scaffoldState,
        drawerBackgroundColor = backgroundColor,
        backgroundColor = backgroundColor,
        contentColor = Color.Red,
        //drawerScrimColor = Color.Red,
        drawerContentColor = Color.Red,
        topBar = {
            MyAppBar(onNavigationIconClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            })
        },//topBar = { TopAppBar { /* Top app bar content */ } },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            Text(text = "text1")
            Text(text = "text2")
            Text(text = "text3")
        }
    ) {
        //Box(modifier = Modifier.background(Color.Red))
        Text(text = "asdasd")
        /*LazyColumn(*//*Modifier.background(Color.Red).fillMaxSize()*//*){
            item{
                Text(text = "asdasd")
            }
        }*/
    }
    //Text(text = "asdasdqweqweqewqwe")
        /*LazyColumn {
            // Add a single item
            item {
                Card(modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)) {
                    Text(
                        text = "List Screen",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }*/

}

    /*Card(modifier = Modifier.padding(46.dp)) {
        Text(
            text = "List Screen",
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )
    }*/
    /*Icon(
        imageVector = Icons.Default.AccountCircle,
        contentDescription = "authentication",
        tint = MaterialTheme.colorScheme.surfaceTint
    )*/
    //OutlinedTextField(modifier = Modifier.fillMaxWidth(),value = password.value, onValueChange = { newText -> password.value = newText })
    /*Text(
        text = "List Screen",
        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
    )*/

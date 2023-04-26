package com.example.forsamsung012.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.forsamsung012.R

enum class MultiFloatingState{
    Expanded,
    Collapsed
}

class MinFabItem(
    val icon: ImageBitmap,/*ImageVector*/
    val label:String,
    val identifier:String
)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskScreen(navController: NavHostController,
                 context: Context) {

    var multiFloatingState by remember {
        mutableStateOf(MultiFloatingState.Collapsed)
    }

    var message1 = remember { mutableStateOf("") }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

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
                multiFloatingState = multiFloatingState,
                onMultiFabStateChange = {
                    multiFloatingState = it
                },
                items = items
            )

                               
            /*Button(onClick = { navController.navigate("TaskScreen")},modifier = Modifier
                .clip(
                    CircleShape
                )
                .size(50.dp)) {
                Icon(Icons.Filled.Add, contentDescription = "Добавить", modifier = Modifier.size(50.dp))
            }*/
                               //////////////////////////////////////////////////////////////
            /*FloatingActionButton(onClick = { navController.navigate("TaskScreen") }) {
                *//* FAB content *//*
                Icon(Icons.Filled.Add, contentDescription = "Добавить")
            }*/
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


            TextField(value = message1.value,
                onValueChange = { message1.value = it },
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


@Composable
fun MultiFloatingButton(
    multiFloatingState : MultiFloatingState,
    onMultiFabStateChange: (MultiFloatingState) -> Unit,
    items:List<MinFabItem>
){
    val transition = updateTransition(targetState = multiFloatingState, label = "transition")

    val rotate by transition.animateFloat(label = "rotate") {
        if (it == MultiFloatingState.Expanded) 315f else 0f
    }

    val fabScale by transition.animateFloat(label = "FabScale") {
        if (it == MultiFloatingState.Expanded) 36f else 0f
    }

    val alpha by transition.animateFloat(label = "alpha",
        transitionSpec = { tween(durationMillis = 50) }
        ) {
        if (it == MultiFloatingState.Expanded) 1f else 0f
    }

    val textShadow by transition.animateDp(label = "alpha",
        transitionSpec = { tween(durationMillis = 50) }
    ) {
        if (it == MultiFloatingState.Expanded) 2.dp else 0.dp
    }

    Column(
        horizontalAlignment = Alignment.End
    ) {
        if (transition.currentState == MultiFloatingState.Expanded){
            items.forEach{
                MinFad(
                    item = it,
                    onMinFabItemClick = {

                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
        FloatingActionButton(onClick = {
            onMultiFabStateChange(
                if (transition.currentState == MultiFloatingState.Expanded){
                    MultiFloatingState.Collapsed
                } else{
                    MultiFloatingState.Expanded
                }
            )
        }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.rotate(rotate)
            )

        }
    }


}


@Composable
fun MinFad(
    item: MinFabItem,
    onMinFabItemClick:(MinFabItem)-> Unit
){
    val buttonColor = MaterialTheme.colors.secondary
    Canvas(modifier = Modifier
        .size(32.dp)
        .clickable(
            interactionSource = MutableInteractionSource(),
            onClick = {
                onMinFabItemClick.invoke(item)
            },
            indication = rememberRipple(
                bounded = false,
                radius = 20.dp,
                color = MaterialTheme.colors.onSurface
            )
        )
    ){
        drawCircle(
            color = buttonColor,
            radius = 46f
        )

    drawImage(
            image = item.icon,
            topLeft = Offset(
                center.x - (item.icon.width / 2),
                center.y - (item.icon.width / 2)

            )
        )
    }
}
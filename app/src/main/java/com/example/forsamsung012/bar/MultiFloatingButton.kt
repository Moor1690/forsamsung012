package com.example.forsamsung012.bar

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.example.forsamsung012.model.TaskModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

enum class MultiFloatingState{
    Expanded,
    Collapsed
}

class MinFabItem(
    val icon: ImageBitmap,/*ImageVector*/
    val label:String,
    val identifier:String
)


@Composable
fun MultiFloatingButton(
    database: FirebaseDatabase,
    auth: FirebaseAuth,
    multiFloatingState : MultiFloatingState,
    onMultiFabStateChange: (MultiFloatingState) -> Unit,
    items:List<MinFabItem>,
    databaseReference: DatabaseReference,
    firebaseRemoteConfig: FirebaseRemoteConfig,
    message1: MutableState<String>,
    message2: MutableState<String>

){
    //val databaseReference = FirebaseDatabase.getInstance().getReference("USERS/${auth.uid}")
    val myRef = database.getReference("0/${auth.uid}/TASK/")
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
            myRef.push().setValue(TaskModel(
                key = "testadd",
                name = message1.value,
                task = message2.value
            ))
            /*databaseReference.push().setValue(
                TaskModel(
                    key = databaseReference.push().key,
                    name = message1.value,
                    task = message2.value
                )
            )*/
        //val myRef = database.getReference("USERS/${auth.uid}/TASK2/")
            /*databaseReference.push().setValue(
                TaskModel(
                    key = databaseReference.push().key,
                    name = message1.value,
                    task = message1.value
                )
            )*/
            /*onMultiFabStateChange(
                    if (transition.currentState == MultiFloatingState.Expanded){
                        MultiFloatingState.Collapsed
                    } else{
                        MultiFloatingState.Expanded
                    }
                )*/
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
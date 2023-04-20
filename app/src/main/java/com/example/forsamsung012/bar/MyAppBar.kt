package com.example.forsamsung012.bar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDefaults
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Composable
fun MyAppBar(
    onNavigationIconClick: () -> Unit
) {
    val message1 = remember { mutableStateOf("") }
    TopAppBar(
        title = {
            /*TextField(
                value = message1.value,
                onValueChange = { message1.value = it }*//*,
                modifier = Modifier.background(Color.DarkGray)*//*
            )*/

            //androidx.compose.material.Text(text = "...")
        },
        backgroundColor = SnackbarDefaults.backgroundColor/*Color.Red*//*MaterialTheme.colors.primary*/,
        contentColor = MaterialTheme.colors.onPrimary,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Toggle drawer"
                )
            }
        }
    )
}
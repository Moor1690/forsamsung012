package com.example.forsamsung012.bar

import androidx.compose.foundation.background
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDefaults
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalSoftwareKeyboardController.current
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MyAppBar(
    listName: MutableState<String>,
    onNavigationIconClick: () -> Unit
) {
    //val listName = remember { mutableStateOf("") }
    TopAppBar(

        title = {
            Text(
                text = listName.value/*,
                modifier = Modifier.background(Color.DarkGray)*/
            )
            /*OutlinedTextField(
                value = listName.value,
                onValueChange = { newText -> listName.value = newText },
                modifier = Modifier.background(Color.DarkGray),
                enabled = false,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Default
                )
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
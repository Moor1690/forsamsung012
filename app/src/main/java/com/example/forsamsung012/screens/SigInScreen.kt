package com.example.forsamsung012.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.SnackbarDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.forsamsung012.viewModel.SignInViewModel
import com.example.forsamsung012.viewModel.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlin.system.exitProcess

@Composable
fun SigInScreen(
    auth: FirebaseAuth,
    navController: NavHostController,
    context: Context
) {

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val signInViewModel: SignInViewModel = SignInViewModel()
    val signUpViewModel: SignUpViewModel = SignUpViewModel()

    val lifecycleOwner = LocalLifecycleOwner.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SnackbarDefaults.backgroundColor)
            .imePadding(),
        // parameters set to place the items in center

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon Composable
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "authentication",
            tint = MaterialTheme.colorScheme.surfaceTint
        )
        // Text to Display the current Screen
        Text(text = "Authentication", color = MaterialTheme.colorScheme.onSurface)
        // OutlinedTextField to type the Email
        OutlinedTextField(
            value = email.value,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            onValueChange = { newText -> email.value = newText },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Email address") },
            placeholder = {
                Text(
                    text = "abc@domain.com",
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )
        // OutlinedTextField to type the password
        OutlinedTextField(
            value = password.value,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            onValueChange = { newText -> password.value = newText },
            modifier = Modifier
                .fillMaxWidth()
                .background(SnackbarDefaults.backgroundColor)//MaterialTheme.colorScheme.background)
                .imePadding(),
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )
        // Registration button
        Button(onClick = {
            signUpViewModel.sigUp(email.value, password.value, context)
            // Check the registration
            /*if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email.value, password.value)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                            Toast.makeText(
                                context,
                                "User successful authorized",
                                Toast.LENGTH_SHORT
                            ).show()
                        else
                            Toast.makeText(context, "User is already exist", Toast.LENGTH_SHORT)
                                .show()
                    }
            } else {
                Toast.makeText(
                    context,
                    "Please enter an email address and a password",
                    Toast.LENGTH_SHORT
                ).show()
            }*/
        }, modifier = Modifier.padding(top = 5.dp)) { Text(text = "Registered") }
        // SignIn button
        Button(onClick = {
            if (email.value == "" || password.value == ""){
                Toast.makeText(context, "Please enter email and password.", Toast.LENGTH_SHORT).show()
            }else {
                Log.d("email and password", email.value + "\t" + password.value)
                signInViewModel.signIn(
                    auth = auth,
                    email.value,
                    password.value,
                    context,
                    navController
                )
            }
            // Authorized user login
            /*if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email.value, password.value)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                            context.startActivity(Intent(context, MainActivity::class.java))
                        else
                            Toast.makeText(
                                context,
                                "Please check that your email address and password are correct",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
            } else {
                Toast.makeText(
                    context, "Please enter an email address and a password", Toast.LENGTH_SHORT
                ).show()
            }*/
        }) { Text(text = "Sign in") }


        BackHandler {
            //if (lifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
            navController.navigate("SigInScreen")
            exitProcess(0)
            //}
        }
    }
}
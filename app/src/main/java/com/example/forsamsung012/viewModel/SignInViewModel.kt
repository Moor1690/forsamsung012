package com.example.forsamsung012.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

class SignInViewModel {
    //private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    //var cUser = auth.currentUser

    fun signIn(
        auth: FirebaseAuth,
        email: String,
        password: String,
        cont: Context?,
        navController: NavHostController
    ) {
        //navController.navigate("ListScreen")

        if (auth.currentUser == null) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(cont, "Authentication Successful.", Toast.LENGTH_SHORT)
                            .show()
                        navController.navigate("ListScreen")
                    } else {
                        Log.d("email and password", email + "\t" + password)
                        Toast.makeText(cont, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            navController.navigate("ListScreen")
        }
    }
}
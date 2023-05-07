package com.example.forsamsung012.viewModel

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignInModel {
    //private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    //var cUser = auth.currentUser

    fun signIn(auth: FirebaseAuth, cUser:FirebaseUser? ,email: String, password: String, cont: Context?, navController: NavHostController){
        //navController.navigate("ListScreen")

        if(auth.currentUser == null){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(cont, "Authentication Successful.", Toast.LENGTH_SHORT).show()
                        navController.navigate("ListScreen")
                    } else {
                        Toast.makeText(cont, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        else{
            navController.navigate("ListScreen")
        }
    }
}
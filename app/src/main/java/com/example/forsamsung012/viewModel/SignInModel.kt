package com.example.forsamsung012.viewModel

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

class SignInModel {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var cUser = auth.currentUser

    fun signIn(email: String, password: String, cont: Context?, navController: NavHostController){
        //navController.navigate("ListScreen")

        if(cUser == null){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(cont, "Authentication Successful.", Toast.LENGTH_SHORT).show()
                        val user = auth.currentUser
                        println("user.toString() " + user.toString())
                        navController.navigate("ListScreen")
                        //frag.findNavController().navigate(R.id.action_mainFragment_to_sigInFragment)
                    } else {
                        Toast.makeText(/*getContext()*/cont, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        else{
            navController.navigate("ListScreen")
        }
    }
}
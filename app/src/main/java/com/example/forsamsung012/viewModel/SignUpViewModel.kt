package com.example.forsamsung012.viewModel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth

class SignUpViewModel (application: Application) :
    AndroidViewModel(application) {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun sigUp(email: String, password: String, cont: Context?) {
        auth.createUserWithEmailAndPassword(email.toString(), password.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(cont, "Registration Successful.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(cont, "Registration failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
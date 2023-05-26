package com.example.forsamsung012.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.forsamsung012.model.MyRepository
import com.example.forsamsung012.model.TaskDatabase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(application: Application) :
    AndroidViewModel(application) {
    //private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    //var cUser = auth.currentUser
    var myR = MyRepository(application, FirebaseAuth.getInstance())
    val taskDAO = TaskDatabase.getDatabase(context = application).taskDAO()
    fun signIn(
        auth: FirebaseAuth,
        email: String,
        password: String,
        cont: Context?,
        navController: NavHostController
    ) {
        check()
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

    fun check(){
        viewModelScope.launch(Dispatchers.IO) {
            myR.check(dataFromRoom = taskDAO.getAllTaskModelL(),nameTaskListFromRoom =  taskDAO.getAllTaskListNameL())
        }
    }
}
package com.example.forsamsung012

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.forsamsung012.navigate.Navigate
import com.example.forsamsung012.ui.theme.Forsamsung012Theme
import com.example.forsamsung012.viewModel.ListScreenViewModel
import com.example.forsamsung012.viewModel.SignInViewModel
import com.example.forsamsung012.viewModel.TaskScreenViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var listScreenViewModel: ListScreenViewModel
    private lateinit var taskScreenViewModel: TaskScreenViewModel
    private lateinit var signInViewModel: SignInViewModel
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Forsamsung012Theme {
                var listName = remember { mutableStateOf("") }
                var taskName = remember { mutableStateOf("") }
                var taskDescription = remember { mutableStateOf("") }

                val navController: NavHostController = rememberAnimatedNavController()
                listScreenViewModel = ListScreenViewModel(application)
                taskScreenViewModel = TaskScreenViewModel(application = application, auth = auth)
                signInViewModel = SignInViewModel(application)
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {

                    Navigate(
                        listName = listName,
                        taskName = taskName,
                        taskDescription = taskDescription,
                        taskScreenViewModel = taskScreenViewModel,
                        listScreenViewModel = listScreenViewModel,
                        signInViewModel = signInViewModel,
                        auth = auth,
                        application = application,
                        navController = navController,
                        context = LocalContext.current
                    )
                }
            }
        }
    }
}
package com.example.forsamsung012

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.forsamsung012.navigate.Navigate
import com.example.forsamsung012.ui.theme.Forsamsung012Theme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Forsamsung012Theme {
                val navController: NavHostController = rememberAnimatedNavController()
                val isShowBottomBar = remember { mutableStateOf(false) }
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize()//,
                    //color = MaterialTheme.colorScheme.background
                ) {
                    Navigate(
                        navController = navController,
                        context = this,
                        isShowBottomBar = isShowBottomBar
                    )
                }
            }
        }
    }
}
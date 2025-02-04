package com.RageRacoon.learm_x_coffee

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.RageRacoon.learm_x_coffee.domain.use_cases.login.Login
import com.RageRacoon.learm_x_coffee.presentation.screens.login.LoginState
import com.RageRacoon.learm_x_coffee.presentation.screens.login.components.LogInContent
import com.RageRacoon.learm_x_coffee.presentation.screens.login.components.Login
import com.RageRacoon.learm_x_coffee.presentation.screens.login.components.LoginBottomBar
import com.RageRacoon.learm_x_coffee.presentation.screens.login.components.LoginTopBar
import com.RageRacoon.learm_x_coffee.presentation.screens.login.components.Login
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LogInScreen(navController : NavHostController) {
   Scaffold(
       topBar = {
           LoginTopBar()
       },
       content = {
           LogInContent (navController)
       },
       bottomBar = {
           LoginBottomBar(navController)
       }
   )
    Login(navController = navController) //Manejando el estado del login, nuevo componente
}
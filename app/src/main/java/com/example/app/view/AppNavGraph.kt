package com.example.app.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.app.viewmodel.CarritoViewModel
import androidx.navigation.navArgument
import com.example.app.model.UsuarioDTO
import com.google.gson.Gson


@Composable
fun AppNavGraph(navController: NavHostController) {


    val carritoViewModel: CarritoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }

        //composable("home") { HomeScreen(navController) }
        composable(
            route = "home/{userJson}",
            arguments = listOf(navArgument("userJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val userJson = backStackEntry.arguments?.getString("userJson")
            val usuario = Gson().fromJson(userJson, UsuarioDTO::class.java)

            HomeScreen(
                navController = navController,
                user = usuario.nombre ?: "Sin nombre",
                email = usuario.email ?: "Sin email"
            )
        }
        composable("qr") { QrScreen(navController) }


    }
}



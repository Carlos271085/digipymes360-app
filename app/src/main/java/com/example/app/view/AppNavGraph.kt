package com.example.app.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.app.viewmodel.CarritoViewModel
import com.example.app.model.UsuarioDTO
import com.google.gson.Gson

@Composable
fun AppNavGraph(navController: NavHostController) {

    val carritoViewModel: CarritoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // LOGIN
        composable("login") {
            LoginScreen(navController)
        }

        // REGISTRO
        composable("register") {
            RegisterScreen(navController)
        }

        // HOME SIMPLE (sin datos de usuario)

        composable(
            route = "home/{userJson}",
            arguments = listOf(navArgument("userJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val userJson = backStackEntry.arguments?.getString("userJson")
            val usuario = Gson().fromJson(userJson, UsuarioDTO::class.java)

            HomeScreen(
                navController = navController,
                viewModel = carritoViewModel,
                user = usuario.nombre ?: "Sin nombre",
                email = usuario.email ?: "Sin email"
            )
        }

        composable(
            route = "profile/{userJson}",
            arguments = listOf(navArgument("userJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val userJson = backStackEntry.arguments?.getString("userJson")
            val usuario = Gson().fromJson(userJson, UsuarioDTO::class.java)

            ProfileScreen(
                navController = navController,
                user = usuario.nombre ?: "Sin nombre",
                email = usuario.email ?: "Sin email"
            )
        }

        // CARRITO DE COMPRAS
        composable("carro_compras") {
            CarroDeComprasScreen(navController, carritoViewModel)
        }

        // COMPRA EXITOSA
        composable("compra_exitosa") {
            CompraExitosaScreen(navController)
        }
    }
}




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

    val gson = Gson().toJson(UsuarioDTO(0,"Usuario de Prueba","email@mail.com","0"))

    NavHost(
        navController = navController,
        //startDestination = "login"
        startDestination = "home" // Test only
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
            route = "home",

        ) {
            HomeScreen(
                navController = navController,
                viewModel = carritoViewModel
            )
        }

        // PERFIL (desde JSON)
        composable(
            route = "profile"
        ) {
            ProfileScreen(
                navController
            )
        }

        // CARRITO DE COMPRAS
        composable("carro_compras") {
            CarroDeComprasScreen(navController, carritoViewModel)
        }

        // COMPRA EXITOSA
        composable("compra_exitosa") { CompraExitosaScreen(navController) }

        // 🆕 NUEVAS RUTAS (DEBEN ESTAR DENTRO DEL NAVHOST)
        composable("perfil_usuario") {
            ProfileScreen(navController)
        }

        composable("historial_compras") {
            HistorialComprasScreen(navController)
        }

        composable("contacto") {
            ContactoScreen(navController)
        }
        composable("compra_exitosa") {
            CompraExitosaScreen(navController)
        }
    }
}




package com.example.app.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.app.viewmodel.CarritoViewModel
import com.example.app.model.UsuarioDTO
import com.example.app.viewmodel.ProductoViewModel
import com.google.gson.Gson

@Composable
fun AppNavGraph(navController: NavHostController) {

    val carritoViewModel: CarritoViewModel = viewModel()
    val productoViewModel: ProductoViewModel = viewModel()

    val gson = Gson().toJson(UsuarioDTO(0, "Usuario de Prueba", "email@mail.com", "0"))

    NavHost(
        navController = navController,
        //startDestination = "login"
        //startDestination = "home" // Test only
        startDestination = "biometric_test"

    ) {

        // LOGIN
        composable("login") {
            LoginScreen(navController)
        }

        // REGISTRO
        composable("register") {
            RegisterScreen(navController)
        }

        // HOME
        composable(
            route = "home",

        ) {
            HomeScreen(
                navController = navController,
                carritoViewModel = carritoViewModel,
                productoViewModel = productoViewModel

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

        // --- PANTALLA DE PRUEBA PARA BIOMETRÍA ---
        composable("biometric_test") {
            val context = LocalContext.current
            val activity = context as FragmentActivity
            com.example.app.ui.screens.BiometricScreen(activity,navController)
        }

        composable("contacto") {
            ContactoScreen(navController)
        }
    }
}


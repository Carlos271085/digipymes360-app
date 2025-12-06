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
import com.example.app.viewmodel.ProductoViewModel
import com.google.gson.Gson
import com.example.app.model.UsuarioDTO

@Composable
fun AppNavGraph(navController: NavHostController) {

    // ViewModels compartidos
    val carritoViewModel: CarritoViewModel = viewModel()
    val productoViewModel: ProductoViewModel = viewModel()

    // Ejemplo de usuario (luego lo conectas a tu login real)
    val gson = Gson().toJson(UsuarioDTO(0, "Usuario de Prueba", "email@mail.com", "0"))

    NavHost(
        navController = navController,
        startDestination = "biometric_test"
        //startDestination = "login"
        //startDestination = "home"
    ) {

        // ---------------------------
        // LOGIN
        // ---------------------------
        composable("login") {
            LoginScreen(navController)
        }

        // ---------------------------
        // REGISTRO
        // ---------------------------
        composable("register") {
            RegisterScreen(navController)
        }

        // ---------------------------
        // HOME
        // ---------------------------
        composable("home") {
            HomeScreen(
                navController = navController,
                carritoViewModel = carritoViewModel,
                productoViewModel = productoViewModel
            )
        }

        // ---------------------------
        // PERFIL
        // ---------------------------
        composable("profile") {
            ProfileScreen(navController)
        }

        // ---------------------------
        // CARRITO DE COMPRAS
        // ---------------------------
        composable("carro_compras") {
            CarroDeComprasScreen(navController, carritoViewModel)
        }

        // ---------------------------
        // COMPRA EXITOSA
        // ---------------------------
        composable("compra_exitosa") {
            CompraExitosaScreen(navController)
        }

        // ---------------------------
        // PERFIL (RUTA EXTRA)
        // ---------------------------
        composable("perfil_usuario") {
            ProfileScreen(navController)
        }

        // ---------------------------
        // HISTORIAL DE COMPRAS
        // ---------------------------
        composable("historial_compras") {
            HistorialComprasScreen(navController)
        }

        // ---------------------------
        // BIOMETRÍA (PRUEBA)
        // ---------------------------
        composable("biometric_test") {
            val context = LocalContext.current
            val activity = context as FragmentActivity
            com.example.app.ui.screens.BiometricScreen(activity, navController)
        }

        // ---------------------------
        // CONTACTO
        // ---------------------------
        composable("contacto") {
            ContactoScreen(navController)
        }

        // =======================================================
        //  PASARELA DE PAGO — RUTA NUEVA
        // =======================================================
        composable(
            route = "pago/{monto}",
            arguments = listOf(
                navArgument("monto") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val monto = backStackEntry.arguments
                ?.getString("monto")
                ?.toDoubleOrNull() ?: 0.0

            PagoScreen(
                navController = navController,
                usuarioId = 1L, // LUEGO LO CONECTAMOS AL LOGIN REAL
                monto = monto
            )
        }
    }
}



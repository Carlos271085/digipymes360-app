package com.example.app

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.app.ui.login.LoginViewModel
import com.example.app.view.RegisterScreen
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import io.mockk.*

@RunWith(AndroidJUnit4::class)
class RegisterScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun muestraCamposBasicos() {
        val nav = TestNavHostController(composeRule.activity).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        // Mock del ViewModel
        val vm = mockk<LoginViewModel>(relaxed = true)

        every { vm.loginResult } returns MutableStateFlow(null)
        every { vm.error } returns MutableStateFlow(null)

        composeRule.setContent {
            RegisterScreen(
                navController = nav,
                viewModel = vm
            )
        }

        composeRule.onNodeWithText("Nombre completo").assertIsDisplayed()
        composeRule.onNodeWithText("Correo electrónico").assertIsDisplayed()
        composeRule.onNodeWithText("Contraseña (mínimo 6 caracteres)").assertIsDisplayed()
        composeRule.onNodeWithText("Dirección completa (Calle, N°, Comuna y Región)").assertIsDisplayed()
        composeRule.onNodeWithText("Número de celular").assertIsDisplayed()
        composeRule.onNodeWithText("Registrarse").assertIsDisplayed()
    }

    @Test
    fun registrarUsuario_LlamaViewModel() {
        val nav = TestNavHostController(composeRule.activity).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        val vm = mockk<LoginViewModel>(relaxed = true)

        every { vm.loginResult } returns MutableStateFlow(null)
        every { vm.error } returns MutableStateFlow(null)

        composeRule.setContent {
            RegisterScreen(
                navController = nav,
                viewModel = vm
            )
        }

        // Completar campos
        composeRule.onNodeWithText("Nombre completo").performTextInput("Juan Perez")
        composeRule.onNodeWithText("Correo electrónico").performTextInput("test@test.com")
        composeRule.onNodeWithText("Contraseña (mínimo 6 caracteres)").performTextInput("123456")
        composeRule.onNodeWithText("Dirección completa (Calle, N°, Comuna y Región)")
            .performTextInput("Calle Falsa 123")
        composeRule.onNodeWithText("Número de celular").performTextInput("12345678")

        // Click en registrar
        composeRule.onNodeWithText("Registrarse").performClick()

        verify {
            vm.registrar(
                any(),      // UsuarioRegistro(nombre, email, password)
                "Calle Falsa 123",
                "12345678"
            )
        }
    }

    @Test
    fun navegarLoginDesdeTexto() {
        val nav = TestNavHostController(composeRule.activity).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        val vm = mockk<LoginViewModel>(relaxed = true)

        every { vm.loginResult } returns MutableStateFlow(null)
        every { vm.error } returns MutableStateFlow(null)

        composeRule.setContent {
            RegisterScreen(
                navController = nav,
                viewModel = vm
            )
        }

        composeRule.onNodeWithText("¿Ya tienes cuenta? Inicia sesión aquí")
            .performClick()

        assert(nav.currentDestination?.route == "login")
    }

    @Test
    fun muestraErrorSiNombreVacio() {
        val vm = mockk<LoginViewModel>(relaxed = true)

        val nav = TestNavHostController(composeRule.activity).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        every { vm.loginResult } returns MutableStateFlow(null)
        every { vm.error } returns MutableStateFlow(null)

        composeRule.setContent {
            RegisterScreen(
                navController = nav,
                viewModel = vm
            )
        }

        // Click registrar sin llenar nada
        composeRule.onNodeWithText("Registrarse").performClick()

        // Debe aparecer snackbar de error
        composeRule.onNodeWithText("El nombre es obligatorio").assertIsDisplayed()
    }
}

package com.example.app

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import androidx.navigation.testing.TestNavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.app.ui.login.LoginViewModel
import com.example.app.view.LoginScreen              // <-- IMPORT CORRECTO
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import io.mockk.*

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun muestraCamposBasicos() {
        val nav = TestNavHostController(composeRule.activity)
        nav.navigatorProvider.addNavigator(ComposeNavigator())

        val vm = mockk<LoginViewModel>(relaxed = true)

        every { vm.loginResult } returns MutableStateFlow(null)
        every { vm.error } returns MutableStateFlow(null)

        composeRule.setContent {
            LoginScreen(
                navController = nav,
                viewModel = vm
            )
        }

        composeRule.onNodeWithText("Correo electrónico").assertIsDisplayed()
        composeRule.onNodeWithText("Contraseña").assertIsDisplayed()
        composeRule.onNodeWithText("Ingresar").assertIsDisplayed()
        composeRule.onNodeWithText("¿No tienes cuenta? Regístrate aquí").assertIsDisplayed()
    }

    @Test
    fun ingresarDatosYLlamarLogin() {
        val nav = TestNavHostController(composeRule.activity)
        nav.navigatorProvider.addNavigator(ComposeNavigator())

        val vm = mockk<LoginViewModel>(relaxed = true)

        every { vm.loginResult } returns MutableStateFlow(null)
        every { vm.error } returns MutableStateFlow(null)

        composeRule.setContent {
            LoginScreen(
                navController = nav,
                viewModel = vm
            )
        }

        composeRule.onNodeWithText("Correo electrónico").performTextInput("test@test.com")
        composeRule.onNodeWithText("Contraseña").performTextInput("123456")
        composeRule.onNodeWithText("Ingresar").performClick()

        verify { vm.login("test@test.com", "123456") }
    }

    @Test
    fun clickNavegarARegistro() {
        val nav = TestNavHostController(composeRule.activity)
        nav.navigatorProvider.addNavigator(ComposeNavigator())

        val vm = mockk<LoginViewModel>(relaxed = true)

        every { vm.loginResult } returns MutableStateFlow(null)
        every { vm.error } returns MutableStateFlow(null)

        composeRule.setContent {
            LoginScreen(
                navController = nav,
                viewModel = vm
            )
        }

        composeRule.onNodeWithText("¿No tienes cuenta? Regístrate aquí").performClick()

        assert(nav.currentDestination?.route == "register")
    }
}

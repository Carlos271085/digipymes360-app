package com.example.app.view

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.app.MainActivity
import com.example.app.viewmodel.PagoViewModel
import com.example.app.ui.state.PagoUiState            // <- IMPORT CORRECTO
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PagoScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private fun mockInitialState() = PagoUiState(
        isLoading = false,
        exito = true,
        mensaje = "Pago realizado",
        urlWebPay = "https://webpay.cl/test",
        publicKey = "pk_test_123"
    )

    @Test
    fun muestraMontoYBotonPago() {
        val nav = TestNavHostController(composeRule.activity)
        nav.navigatorProvider.addNavigator(ComposeNavigator())

        val vm = mockk<PagoViewModel>()

        every { vm.uiState } returns MutableStateFlow(mockInitialState())

        composeRule.setContent {
            PagoScreen(
                navController = nav,
                usuarioId = 10L,
                monto = 9990.0,
                viewModel = vm
            )
        }

        composeRule.onNodeWithText("Total a pagar:").assertIsDisplayed()
        composeRule.onNodeWithText("$9990.0").assertIsDisplayed()
        composeRule.onNodeWithText("Confirmar Pago").assertIsDisplayed()
    }

    // ... el resto de tests idéntico, usando PagoUiState del paquete com.example.app.ui.state
}

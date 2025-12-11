package com.example.app.view

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import androidx.navigation.testing.TestNavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.app.MainActivity
import com.example.app.viewmodel.CarritoViewModel
import com.example.app.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import io.mockk.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CarroDeComprasScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private fun mockProducto(
        nombre: String = "Producto Test",
        precio: String = "$10.000",
        stock: Int = 1
    ) = Producto(
        id = 1,
        nombre = nombre,
        precio = precio,
        descripcion = "",
        idNegocio = 1,
        stock = stock,

    )

    @Test
    fun muestraMensajeCarritoVacio() {
        val nav = TestNavHostController(composeRule.activity)
        nav.navigatorProvider.addNavigator(ComposeNavigator())

        val vm = mockk<CarritoViewModel>()

        every { vm.carrito } returns MutableStateFlow(emptyList())
        every { vm.totalProductos } returns MutableStateFlow(0)

        composeRule.setContent {
            CarroDeComprasScreen(
                navController = nav,
                viewModel = vm
            )
        }

        composeRule.onNodeWithText("Tu carrito está vacío").assertIsDisplayed()
    }

    @Test
    fun muestraProductosEnCarrito() {
        val nav = TestNavHostController(composeRule.activity)
        nav.navigatorProvider.addNavigator(ComposeNavigator())

        val producto = mockProducto()

        val vm = mockk<CarritoViewModel>()

        every { vm.carrito } returns MutableStateFlow(listOf(producto))
        every { vm.totalProductos } returns MutableStateFlow(1)
        every { vm.totalCompra() } returns 10000.0

        composeRule.setContent {
            CarroDeComprasScreen(nav, vm)
        }

        composeRule.onNodeWithText("Producto Test").assertIsDisplayed()
        composeRule.onNodeWithText("$10.000").assertIsDisplayed()
    }

    @Test
    fun eliminarProductoDelCarrito() {
        val nav = TestNavHostController(composeRule.activity)
        nav.navigatorProvider.addNavigator(ComposeNavigator())

        val producto = mockProducto()

        val vm = mockk<CarritoViewModel>(relaxed = true)

        every { vm.carrito } returns MutableStateFlow(listOf(producto))
        every { vm.totalProductos } returns MutableStateFlow(1)
        every { vm.totalCompra() } returns 10000.0

        composeRule.setContent {
            CarroDeComprasScreen(nav, vm)
        }

        composeRule.onNodeWithContentDescription("Eliminar producto").performClick()

        verify { vm.eliminarDelCarrito(producto) }
    }

    @Test
    fun botonVaciarCarritoLlamaMetodo() {
        val nav = TestNavHostController(composeRule.activity)
        nav.navigatorProvider.addNavigator(ComposeNavigator())

        val producto = mockProducto()

        val vm = mockk<CarritoViewModel>(relaxed = true)

        every { vm.carrito } returns MutableStateFlow(listOf(producto))
        every { vm.totalProductos } returns MutableStateFlow(1)
        every { vm.totalCompra() } returns 10000.0

        composeRule.setContent {
            CarroDeComprasScreen(nav, vm)
        }

        composeRule.onNodeWithText("Vaciar carrito").performClick()

        verify { vm.vaciarCarrito() }
    }

    @Test
    fun navegarAlPagoConTotal() {
        val nav = TestNavHostController(composeRule.activity)
        nav.navigatorProvider.addNavigator(ComposeNavigator())

        val producto = mockProducto()

        val vm = mockk<CarritoViewModel>()

        every { vm.carrito } returns MutableStateFlow(listOf(producto))
        every { vm.totalProductos } returns MutableStateFlow(1)
        every { vm.totalCompra() } returns 5000.0

        composeRule.setContent {
            CarroDeComprasScreen(nav, vm)
        }

        composeRule.onNodeWithText("Proceder al pago").performClick()

        assert(nav.currentDestination?.route?.startsWith("pago") == true)
    }

    @Test
    fun navegarAHome() {
        val nav = TestNavHostController(composeRule.activity)
        nav.navigatorProvider.addNavigator(ComposeNavigator())

        val producto = mockProducto()

        val vm = mockk<CarritoViewModel>()

        every { vm.carrito } returns MutableStateFlow(listOf(producto))
        every { vm.totalProductos } returns MutableStateFlow(1)
        every { vm.totalCompra() } returns 5000.0

        composeRule.setContent {
            CarroDeComprasScreen(nav, vm)
        }

        composeRule.onNodeWithText("Seguir comprando").performClick()

        assert(nav.currentDestination?.route == "home")
    }
}

package com.example.app

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.app.model.Producto
import com.example.app.view.HomeScreen
import com.example.app.viewmodel.CarritoViewModel
import com.example.app.viewmodel.ProductoViewModel
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private fun mockProducto(nombre: String, precio: String): Producto =
        Producto(
            nombre = nombre,
            precio = precio,
            descripcion = "",
            id = 0,
            idNegocio = 0,
            stock = 0
        )


    @Test
    fun cargaPantallaHome() {
        val nav = TestNavHostController(composeRule.activity).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        // Mock de viewmodels
        val carritoVM = mockk<CarritoViewModel>(relaxed = true)
        val productoVM = mockk<ProductoViewModel>(relaxed = true)

        every { carritoVM.totalProductos } returns MutableStateFlow(0)
        every { productoVM.productos } returns MutableStateFlow(emptyList())
        every { productoVM.loading } returns MutableStateFlow(false)
        every { productoVM.error } returns MutableStateFlow(null)

        composeRule.setContent {
            HomeScreen(
                navController = nav,
                carritoViewModel = carritoVM,
                productoViewModel = productoVM
            )
        }

        composeRule.onNodeWithText("PYMES 360").assertIsDisplayed()
        composeRule.onNodeWithText("Buscar productos...").assertIsDisplayed()
        composeRule.onNodeWithText("Bienvenido a PYMES 360").assertIsDisplayed()
    }

    @Test
    fun muestraProductosYPermiteFiltrar() {
        val nav = TestNavHostController(composeRule.activity).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        val carritoVM = mockk<CarritoViewModel>(relaxed = true)
        val productoVM = mockk<ProductoViewModel>(relaxed = true)

        val lista = listOf(
            mockProducto("Café Premium", "$5000"),
            mockProducto("Mermelada Artesanal", "$3500")
        )

        every { carritoVM.totalProductos } returns MutableStateFlow(0)
        every { productoVM.productos } returns MutableStateFlow(lista)
        every { productoVM.loading } returns MutableStateFlow(false)
        every { productoVM.error } returns MutableStateFlow(null)

        composeRule.setContent {
            HomeScreen(
                navController = nav,
                carritoViewModel = carritoVM,
                productoViewModel = productoVM
            )
        }

        // Ambos productos visibles
        composeRule.onNodeWithText("Café Premium").assertIsDisplayed()
        composeRule.onNodeWithText("Mermelada Artesanal").assertIsDisplayed()

        // Filtrar
        composeRule.onNodeWithText("Buscar productos...").performTextInput("café")

        // Debe quedar solo uno visible
        composeRule.onNodeWithText("Café Premium").assertIsDisplayed()
        composeRule.onNodeWithText("Mermelada Artesanal").assertDoesNotExist()
    }

    @Test
    fun agregarAlCarritoLlamaViewModel() {
        val nav = TestNavHostController(composeRule.activity).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        val carritoVM = mockk<CarritoViewModel>(relaxed = true)
        val productoVM = mockk<ProductoViewModel>(relaxed = true)

        val producto = mockProducto("Pan Amasado", "$2000")

        every { carritoVM.totalProductos } returns MutableStateFlow(0)
        every { productoVM.productos } returns MutableStateFlow(listOf(producto))
        every { productoVM.loading } returns MutableStateFlow(false)
        every { productoVM.error } returns MutableStateFlow(null)

        composeRule.setContent {
            HomeScreen(
                navController = nav,
                carritoViewModel = carritoVM,
                productoViewModel = productoVM
            )
        }

        // Click botón agregar
        composeRule.onNodeWithContentDescription("Agregar al carrito")
            .performClick()

        verify { carritoVM.agregarAlCarrito(producto) }
    }

    @Test
    fun navegarAlCarrito() {
        val nav = TestNavHostController(composeRule.activity).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        val carritoVM = mockk<CarritoViewModel>(relaxed = true)
        val productoVM = mockk<ProductoViewModel>(relaxed = true)

        every { carritoVM.totalProductos } returns MutableStateFlow(3)
        every { productoVM.productos } returns MutableStateFlow(emptyList())
        every { productoVM.loading } returns MutableStateFlow(false)
        every { productoVM.error } returns MutableStateFlow(null)

        composeRule.setContent {
            HomeScreen(
                navController = nav,
                carritoViewModel = carritoVM,
                productoViewModel = productoVM
            )
        }

        composeRule.onNodeWithContentDescription("Carrito")
            .performClick()

        assert(nav.currentDestination?.route == "carro_compras")
    }

    @Test
    fun mostrarLoading() {
        val nav = TestNavHostController(composeRule.activity).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        val carritoVM = mockk<CarritoViewModel>(relaxed = true)
        val productoVM = mockk<ProductoViewModel>(relaxed = true)

        every { carritoVM.totalProductos } returns MutableStateFlow(0)
        every { productoVM.productos } returns MutableStateFlow(emptyList())
        every { productoVM.loading } returns MutableStateFlow(true)
        every { productoVM.error } returns MutableStateFlow(null)

        composeRule.setContent {
            HomeScreen(
                navController = nav,
                carritoViewModel = carritoVM,
                productoViewModel = productoVM
            )
        }

        composeRule.onNode(hasTestTag("")) // fallback
        composeRule.onNode(isDialog()).assertDoesNotExist()

        composeRule.onNodeWithContentDescription("CircularProgressIndicator")
            .assertExists()
    }

    @Test
    fun mostrarError() {
        val nav = TestNavHostController(composeRule.activity).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        val carritoVM = mockk<CarritoViewModel>(relaxed = true)
        val productoVM = mockk<ProductoViewModel>(relaxed = true)

        every { carritoVM.totalProductos } returns MutableStateFlow(0)
        every { productoVM.productos } returns MutableStateFlow(emptyList())
        every { productoVM.loading } returns MutableStateFlow(false)
        every { productoVM.error } returns MutableStateFlow("Error al cargar productos")

        composeRule.setContent {
            HomeScreen(
                navController = nav,
                carritoViewModel = carritoVM,
                productoViewModel = productoVM
            )
        }

        composeRule.onNodeWithText("Error al cargar productos")
            .assertIsDisplayed()
    }
}

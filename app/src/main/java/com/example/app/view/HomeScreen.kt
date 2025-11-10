package com.example.app.view

import androidx.compose.foundation.Image

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app.R

import com.example.app.ui.theme.*
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.CircleShape
import com.example.app.model.Producto
import com.example.app.viewmodel.CarritoViewModel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: CarritoViewModel, user:String, email:String) {

    var searchQuery by remember { mutableStateOf("") }
    var searchActive by remember { mutableStateOf(false) }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    // --- DATOS DE PRODUCTOS ---
    val productos = listOf(
        Producto(1, 101, 12, "Audífonos Bluetooth", "$14.990", R.drawable.audifono, "Audífonos inalámbricos..."),
        Producto(2, 102, 8, "Smartwatch Deportivo", "$25.990", R.drawable.reloj, "Reloj inteligente..."),
        Producto(3, 103, 10, "Parlante Portátil", "$19.990", R.drawable.microfono, "Parlante Bluetooth..."),
        Producto(4, 104, 15, "Teclado Mecánico RGB", "$29.990", R.drawable.teclado, "Teclado mecánico..."),
        Producto(5, 105, 20, "Mouse Gamer RGB", "$17.990", R.drawable.mouse, "Mouse óptico gamer..."),
        Producto(6, 106, 9, "Cámara Web HD 1080p", "$22.990", R.drawable.camara, "Cámara Full HD...")
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        // --- BARRA DE BÚSQUEDA ---
        DockedSearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = { searchActive = false },
            active = searchActive,
            onActiveChange = { searchActive = it },
            placeholder = { Text("Buscar productos...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = BlueDark) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {}

        // --- RESEÑA DE LA PÁGINA ---
        Text(
            text = "Bienvenido a PYMES 360, tu marketplace digital para emprendedores y pequeños negocios...",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            textAlign = TextAlign.Center
        )

        // --- GRID DE PRODUCTOS ---
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val listaFiltrada = productos.filter {
                it.nombre.contains(searchQuery, ignoreCase = true)
            }

            items(listaFiltrada) { producto ->
                Card(
                    modifier = Modifier
                        .height(260.dp)
                        .clickable { /* futuro detalle */ },
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = MaterialTheme.shapes.large
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = producto.imagen),
                            contentDescription = producto.nombre,
                            modifier = Modifier.size(100.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            producto.nombre,
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            producto.precio,
                            color = OrangePrimary,
                            style = MaterialTheme.typography.labelLarge
                        )

                        Spacer(Modifier.height(8.dp))
                        Button(
                            onClick = { viewModel.agregarAlCarrito(producto) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = OrangePrimary,
                                contentColor = Color.White
                            ),
                            modifier = Modifier.wrapContentSize()
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.agregar_producto),
                                contentDescription = "Agregar al carrito",
                                modifier = Modifier.size(24.dp),
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

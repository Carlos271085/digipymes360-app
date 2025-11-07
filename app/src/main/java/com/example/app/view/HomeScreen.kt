package com.example.app.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app.R
import com.example.app.ui.theme.*
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.CircleShape
import com.example.app.viewmodel.CarritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: CarritoViewModel) {

    var searchQuery by remember { mutableStateOf("") }
    var searchActive by remember { mutableStateOf(false) }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    // --- DATOS DE PRODUCTOS ---
    val productos = listOf(
        Producto(1,
            101,
            12,
            "Audífonos Bluetooth",
            "$14.990",
            R.drawable.audifono,
            "Audífonos inalámbricos con cancelación de ruido, micrófono integrado y hasta 20 horas de batería."),

        Producto(2,
            102,
            8,
            "Smartwatch Deportivo",
            "$25.990",
            R.drawable.reloj,
            "Reloj inteligente con monitor de frecuencia cardíaca, podómetro y resistencia al agua IP68."),

        Producto(3,
            103,
            10,
            "Parlante Portátil",
            "$19.990",
            R.drawable.microfono,
            "Parlante Bluetooth 5.0 con sonido estéreo envolvente y batería de 8 horas."),

        Producto(4,
            104,
            15,
            "Teclado Mecánico RGB",
            "$29.990",
            R.drawable.teclado,
            "Teclado mecánico retroiluminado con switches de alta precisión y diseño ergonómico."),

        Producto(5,
            105,
            20,
            "Mouse Gamer RGB",
            "$17.990",
            R.drawable.mouse,
            "Mouse óptico gamer con sensor 6400 DPI, 7 botones programables y luces RGB."),

        Producto(6,
            106,
            9,
            "Cámara Web HD 1080p",
            "$22.990",
            R.drawable.camara,
            "Cámara Full HD 1080p con micrófono estéreo integrado y enfoque automático.")
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        // --- APP BAR SUPERIOR ---
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        "PYMES 360",
                        fontSize = 22.sp,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = BlueDark
                ),
                actions = {
                    // --- CARRITO CON CONTADOR ---
                    Box {
                        IconButton(onClick = { navController.navigate("carro_compras") }) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = "Carrito",
                                tint = Color.White
                            )
                        }
                        if (viewModel.totalProductos.value > 0) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(x = (-2).dp, y = 2.dp)
                                    .size(18.dp)
                                    .background(OrangePrimary, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = viewModel.totalProductos.value.toString(),
                                    color = Color.White,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },

        // --- BARRA DE NAVEGACIÓN INFERIOR ---
        bottomBar = {
            NavigationBar(containerColor = BlueDark) {
                NavigationBarItem(
                    selected = true,
                    onClick = { /* estás en inicio */ },
                    icon = { Icon(Icons.Default.Search, contentDescription = "Inicio", tint = OrangePrimary) },
                    label = { Text("Inicio", color = Color.White) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("carro_compras") },
                    icon = {
                        Box {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito", tint = Color.White)
                            if (viewModel.totalProductos.value > 0) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .offset(x = 6.dp, y = (-2).dp)
                                        .size(14.dp)
                                        .background(OrangePrimary, shape = CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = viewModel.totalProductos.value.toString(),
                                        color = Color.White,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }
                    },
                    label = { Text("Carrito", color = Color.White) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { /* futuro perfil */ },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color.White) },
                    label = { Text("Perfil", color = Color.White) }
                )
            }
        }
    ) { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .background(LightBackground)
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
                text = "Bienvenido a PYMES 360, tu marketplace digital para emprendedores y pequeños negocios. Aquí podrás explorar productos, comparar precios y apoyar a las pymes locales con cada compra.",
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

                            // --- BOTÓN PARA AGREGAR AL CARRITO ---
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
}

// --- Modelo de producto ---
data class Producto(
    val id: Int,
    val idNegocio: Int,
    var stock: Int,
    val nombre: String,
    val precio: String,
    val imagen: Int,
    val descripcion: String
)

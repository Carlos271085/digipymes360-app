package com.example.app.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.app.R
import com.example.app.model.Producto
import com.example.app.ui.theme.*
import com.example.app.viewmodel.CarritoViewModel
import com.example.app.viewmodel.ProductoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel,
    productoViewModel: ProductoViewModel = hiltViewModel()
) {

    // --- ESTADOS ---
    var searchQuery by remember { mutableStateOf("") }
    var searchActive by remember { mutableStateOf(false) }

    // IMPORTANTE: usamos collectAsState(initial = ...) para evitar ambigüedad de tipo
    val productos: List<Producto> by productoViewModel.productos.collectAsState(initial = emptyList())
    val loading: Boolean by productoViewModel.loading.collectAsState(initial = false)
    val error: String? by productoViewModel.error.collectAsState(initial = null)

    // --- MENU LATERAL ---
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val menuItems = listOf(
        "Perfil del Usuario" to Icons.Default.Person,
        "Listado de Productos" to Icons.Default.Search,
        "Contacto" to Icons.Default.Info
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White,
                drawerTonalElevation = 6.dp
            ) {
                Text(
                    text = "Menú Principal",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp),
                    color = BlueDark
                )
                Divider()

                menuItems.forEach { (titulo, icono) ->
                    NavigationDrawerItem(
                        label = { Text(titulo) },
                        selected = false,
                        onClick = {
                            when (titulo) {
                                "Perfil del Usuario" -> navController.navigate("perfil_usuario")
                                "Listado de Productos" -> navController.navigate("home")
                                "Contacto" -> navController.navigate("contacto")
                            }
                            scope.launch { drawerState.close() }
                        },
                        icon = { Icon(icono, contentDescription = titulo) },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {

        // --- CONTENIDO PRINCIPAL ---
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("PYMES 360") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = BlueDark,
                        titleContentColor = Color.White
                    ),
                    actions = {
                        val totalProductos by carritoViewModel.totalProductos.collectAsState(initial = 0)
                        BadgedBox(
                            badge = {
                                if (totalProductos > 0) {
                                    Badge { Text("$totalProductos") }
                                }
                            }
                        ) {
                            IconButton(onClick = { navController.navigate("carro_compras") }) {
                                Icon(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = "Carrito",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(8.dp)
            ) {

                // --- SEARCH BAR ---
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

                // --- DESCRIPCIÓN ---
                Text(
                    text = "Bienvenido a PYMES 360, tu marketplace digital para emprendedores...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    textAlign = TextAlign.Center
                )

                // --- LOADING ---
                if (loading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = OrangePrimary)
                    }
                }

                // --- ERROR ---
                error?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                // --- LISTA DE PRODUCTOS ---
                val listaFiltrada = productos.filter {
                    it.nombre.contains(searchQuery, ignoreCase = true)
                }

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 160.dp),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(listaFiltrada) { producto ->
                        Card(
                            modifier = Modifier
                                .height(260.dp)
                                .clickable { /* futuro detalle */ },
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
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
                                    painter = painterResource(id = R.drawable.pyme),
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

                                Spacer(Modifier.height(4.dp))

                                Text(
                                    producto.precio,
                                    color = OrangePrimary,
                                    style = MaterialTheme.typography.labelLarge
                                )

                                Spacer(Modifier.height(8.dp))

                                Button(
                                    onClick = { carritoViewModel.agregarAlCarrito(producto) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = OrangePrimary,
                                        contentColor = Color.White
                                    ),
                                    modifier = Modifier.wrapContentSize()
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.agregar_producto),
                                        contentDescription = "Agregar al carrito",
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

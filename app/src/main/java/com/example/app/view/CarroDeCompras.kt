package com.example.app.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app.R
import com.example.app.ui.theme.*
import com.example.app.viewmodel.CarritoViewModel
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.app.model.Producto;
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarroDeComprasScreen(navController: NavController, viewModel: CarritoViewModel) {
    val carrito = viewModel.carrito

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carro de Compras") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlueDark,
                    titleContentColor = Color.White
                )
            )
        }
    ) { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {

            if (carrito.isEmpty()) {
                // --- MENSAJE CUANDO EL CARRITO ESTÁ VACÍO ---
                Text(
                    text = "Tu carrito está vacío",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextSecondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 60.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                // --- LISTADO DE PRODUCTOS EN EL CARRITO ---
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(carrito) { producto ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = producto.imagen),
                                    contentDescription = producto.nombre,
                                    modifier = Modifier.size(80.dp)
                                )

                                Spacer(Modifier.width(12.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        producto.nombre,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = TextPrimary
                                    )
                                    Text(
                                        producto.precio,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = OrangePrimary
                                    )
                                    // --- MOSTRAR CANTIDAD ---
                                    Text(
                                        "Cantidad: ${producto.stock}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextSecondary
                                    )
                                }

                                // --- BOTÓN PARA ELIMINAR ---
                                IconButton(onClick = { viewModel.eliminarDelCarrito(producto) }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.eliminar_compra),
                                        contentDescription = "Eliminar producto",
                                        tint = RedError
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // --- TOTAL DE COMPRA ---
                val total = viewModel.totalCompra()

                Text(
                    text = "Total: $$total",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // --- BOTÓN PARA COMPRAR CARRITO ---
                val context = LocalContext.current
                Button(
                    onClick = {
                        val comprado = viewModel.comprarCarrito()
                        if (comprado) {
                            Toast.makeText(context, "¡Compra realizada con éxito!", Toast.LENGTH_SHORT).show()
                            navController.navigate("compra_exitosa") {
                                popUpTo("carro_compras") { inclusive = true }
                            }
                        } else {
                            Toast.makeText(context, "El carrito está vacío", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Comprar ahora", color = Color.White)
                }


                Spacer(Modifier.height(16.dp))

                // --- BOTÓN PARA VACIAR CARRITO ---
                Button(
                    onClick = { viewModel.vaciarCarrito() },
                    colors = ButtonDefaults.buttonColors(containerColor = RedError),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Vaciar carrito", color = Color.White)
                }

                Spacer(Modifier.height(8.dp))

                // --- BOTÓN PARA VOLVER AL HOME ---
                OutlinedButton(
                    onClick = { navController.navigate("home") },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = BlueDark),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Seguir comprando")
                }
            }
        }
    }
}
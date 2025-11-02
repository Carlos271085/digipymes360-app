package com.example.app.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.app.viewmodel.WalletViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovementsScreen(
    navController: NavController,
    vm: WalletViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val token = "TOKEN_DE_PRUEBA"
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) { vm.loadHome(token) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movimientos") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { pad ->
        LazyColumn(Modifier.padding(pad)) {
            items(state.recent.size) { i ->
                val tx = state.recent[i]
                ListItem(
                    headlineContent = { Text(tx.concept ?: "Transacción") },
                    supportingContent = { Text(tx.createdAt) },
                    trailingContent = { Text("${tx.amount} ${tx.currency}") }
                )
                Divider()
            }
        }
    }
}


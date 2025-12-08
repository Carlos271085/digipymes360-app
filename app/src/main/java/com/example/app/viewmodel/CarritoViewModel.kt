package com.example.app.viewmodel

import androidx.lifecycle.ViewModel
import com.example.app.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CarritoViewModel : ViewModel() {

    // --- LISTA DE PRODUCTOS COMO STATEFLOW ---
    private val _carrito = MutableStateFlow<List<Producto>>(emptyList())
    val carrito: StateFlow<List<Producto>> = _carrito.asStateFlow()

    // --- CONTADOR TOTAL ---
    private val _totalProductos = MutableStateFlow(0)
    val totalProductos: StateFlow<Int> = _totalProductos.asStateFlow()

    // --- AGREGAR PRODUCTO ---
    fun agregarAlCarrito(producto: Producto) {
        val listaActual = _carrito.value.toMutableList()
        val existente = listaActual.find { it.id == producto.id }

        if (existente != null) {
            val actualizado = existente.copy(stock = existente.stock + 1)
            listaActual[listaActual.indexOf(existente)] = actualizado
        } else {
            listaActual.add(producto.copy(stock = 1))
        }

        _carrito.value = listaActual
        actualizarTotal()
    }

    // --- ELIMINAR PRODUCTO ---
    fun eliminarDelCarrito(producto: Producto) {
        val listaActual = _carrito.value.toMutableList()
        val existente = listaActual.find { it.id == producto.id }

        if (existente != null) {
            if (existente.stock > 1) {
                val actualizado = existente.copy(stock = existente.stock - 1)
                listaActual[listaActual.indexOf(existente)] = actualizado
            } else {
                listaActual.remove(existente)
            }
        }

        _carrito.value = listaActual
        actualizarTotal()
    }

    // --- VACIAR CARRITO ---
    fun vaciarCarrito() {
        _carrito.value = emptyList()
        _totalProductos.value = 0
    }

    // --- COMPRAR ---
    fun comprarCarrito(): Boolean {
        return if (_carrito.value.isNotEmpty()) {
            _carrito.value = emptyList()
            _totalProductos.value = 0
            true
        } else {
            false
        }
    }

    // --- TOTAL COMPRA ---
    fun totalCompra(): Double =
        _carrito.value.sumOf { producto ->
            producto.precio
                .replace("$", "")
                .replace(" ", "")
                // Quitar solo comas de miles (si existen)
                .replace(",", "")
                .toDouble() * producto.stock
        }


    // --- ACTUALIZAR CONTADOR ---
    private fun actualizarTotal() {
        _totalProductos.value = _carrito.value.sumOf { it.stock }
    }
}

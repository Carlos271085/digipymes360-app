package com.example.app.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.app.model.Producto


class CarritoViewModel : ViewModel() {

    private val _carrito = mutableStateListOf<Producto>()
    val carrito: List<Producto> get() = _carrito

    // --- CONTADOR OBSERVABLE ---
    var totalProductos = mutableStateOf(0)
        private set

    fun agregarAlCarrito(producto: Producto) {
        // Buscar si el producto ya está en el carrito
        val existente = _carrito.find { it.id == producto.id }

        if (existente != null) {
            // Aumentar cantidad (usamos stock como cantidad en carrito)
            existente.stock += 1
        } else {
            // Agregar nuevo producto con cantidad = 1
            val nuevo = producto.copy(stock = 1)
            _carrito.add(nuevo)
        }

        // 🔁 Actualizar contador reactivo
        totalProductos.value = _carrito.sumOf { it.stock }
    }

    fun eliminarDelCarrito(producto: Producto) {
        val existente = _carrito.find { it.id == producto.id }

        if (existente != null) {
            if (existente.stock > 1) {
                existente.stock -= 1
            } else {
                _carrito.remove(existente)
            }
        }

        // 🔁 Actualizar contador reactivo
        totalProductos.value = _carrito.sumOf { it.stock }
    }

    fun vaciarCarrito() {
        _carrito.clear()
        totalProductos.value = 0
    }

    fun comprarCarrito(): Boolean {
        return if (_carrito.isNotEmpty()) {
            _carrito.clear()
            totalProductos.value = 0
            true
        } else {
            false
        }
    }
}



package com.example.app.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.app.view.Producto

class CarritoViewModel : ViewModel() {

    private val _carrito = mutableStateListOf<Producto>()
    val carrito: List<Producto> get() = _carrito

    //CONTADOR DE PRODUCTOS
    val totalProductos: Int
        get() = _carrito.size

    fun agregarAlCarrito(producto: Producto) {
        if (!_carrito.contains(producto)) {
            _carrito.add(producto)
        }
    }

    fun eliminarDelCarrito(producto: Producto) {
        _carrito.remove(producto)
    }

    fun vaciarCarrito() {
        _carrito.clear()
    }
}

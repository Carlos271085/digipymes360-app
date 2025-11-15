package com.example.app.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.remote.NetworkModule_Cliente
import com.example.app.data.repository.ClienteRepository
import com.example.app.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductoViewModel : ViewModel() {

    private val repository = ClienteRepository(NetworkModule_Cliente.api)

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        cargarProductos()
    }

    fun cargarProductos(min: Float = 0f, max: Float = 999999f) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null
                val lista = repository.getProductos(min, max)
                _productos.value = lista
            } catch (e: Exception) {
                _error.value = "Error cargando productos: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}

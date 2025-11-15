package com.example.app.data.repository

import com.example.app.model.Usuario
import com.example.app.model.UsuarioDTO
import com.example.app.data.remote.ApiService
import com.example.app.data.remote.ApiService_Cliente
import com.example.app.model.Producto
import com.example.app.model.UsuarioRegistro

class ClienteRepository(private val api: ApiService_Cliente) {

    suspend fun getProductos(minimo: Float,maximo: Float): List<Producto> {
        return api.getProductos(minimo,maximo)
    }

}

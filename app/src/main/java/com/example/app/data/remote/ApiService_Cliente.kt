package com.example.app.data.remote

import com.example.app.model.Producto
import com.example.app.model.Usuario
import com.example.app.model.UsuarioDTO
import retrofit2.http.*

interface ApiService_Cliente {
    @GET("/api/v1/producto/precio")
    suspend fun getProductos(
        @Query("minimo") minimo: Float,
        @Query("maximo") maximo: Float
    ): List<Producto>
}

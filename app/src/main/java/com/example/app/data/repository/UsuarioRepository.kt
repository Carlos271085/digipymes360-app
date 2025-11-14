package com.example.app.data.repository

import com.example.app.model.Usuario
import com.example.app.model.UsuarioDTO
import com.example.app.data.remote.ApiService
import com.example.app.model.UsuarioRegistro

class UsuarioRepository(private val api: ApiService) {

    suspend fun login(email: String, password: String): Boolean =
        api.loginDP360(email, password)

    suspend fun loginInfo(email:String, password:String): UsuarioDTO =
        api.loginINFO(email,password)

    suspend fun addUsuario(usuario: UsuarioRegistro, direccion:String,telefono:String): Usuario =
        api.addUsuario(usuario,direccion,telefono)

}

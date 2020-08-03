package br.com.sigo.consultoria.dtos

import java.io.Serializable

data class UsuarioDTO(var id: Int, var codigo: Int, var senha: String, var nome: String) : Serializable
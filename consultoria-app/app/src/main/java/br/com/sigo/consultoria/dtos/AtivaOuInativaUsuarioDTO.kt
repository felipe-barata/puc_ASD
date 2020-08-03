package br.com.sigo.consultoria.dtos

import java.io.Serializable

data class AtivaOuInativaUsuarioDTO(var codigoUsuario: Int, var ativo: Boolean) : Serializable
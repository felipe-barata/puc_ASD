package br.com.sigo.consultoria.dtos

import java.io.Serializable

data class ConsultaNormaDTO(
    var page: Int,
    var size: Int,
    var ordenacao: String,
    var param: String
) : Serializable
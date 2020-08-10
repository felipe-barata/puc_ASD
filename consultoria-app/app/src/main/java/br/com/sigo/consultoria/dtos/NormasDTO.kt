package br.com.sigo.consultoria.dtos

import java.io.Serializable

data class NormasDTO(
    var categoria: String,
    var tipo: String,
    var titulo: String,
    var norma: String,
    var idNorma: Int
) : Serializable
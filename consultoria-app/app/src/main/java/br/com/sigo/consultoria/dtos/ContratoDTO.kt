package br.com.sigo.consultoria.dtos

import java.io.Serializable
import java.time.LocalDate

data class ContratoDTO(
    var id: Int,
    var inicio: LocalDate,
    var fim: LocalDate,
    var valor: Double,
    var horas: Int
) : Serializable
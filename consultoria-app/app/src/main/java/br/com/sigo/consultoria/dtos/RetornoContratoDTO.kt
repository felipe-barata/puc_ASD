package br.com.sigo.consultoria.dtos

import java.io.Serializable
import java.time.LocalDate

data class RetornoContratoDTO(
    var id: Int,
    var inicio: LocalDate,
    var fim: LocalDate,
    var valor: Double,
    var horas: Int,
    var horasContabilizadas: Int,
    var relatorios: List<ListaArquivosContratosDTO>
) : Serializable
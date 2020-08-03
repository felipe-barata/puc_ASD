package br.com.sigo.consultoria.dtos

import java.io.Serializable

data class AtribuirCategoriaEmpresaDTO(var cnpj: String, var categoria: Int) : Serializable
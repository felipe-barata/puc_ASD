package br.com.sigo.consultoria.dtos

import java.io.Serializable

data class EmpresaDTO(
    var id: Int,
    var cnpj: String,
    var nomeEmpresa: String,
    var codigoPostal: String,
    var codigoPais: Int,
    var estado: String,
    var cidade: String,
    var bairro: String,
    var endereco: String,
    var numero: String,
    var categoria: Int
) : Serializable
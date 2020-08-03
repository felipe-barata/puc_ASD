package br.com.sigo.consultoria.enums

import java.io.Serializable

enum class EnumAtributosPesquisaUsuario(
    val param: String,
    val atributo: String,
    val atributoJoin: String
) : Serializable {
    ID("id", "id", ""), NOME("nome", "nome", ""), EMPRESA("empresa", "empresa_id", "usuario_id");

}
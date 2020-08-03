package br.com.sigo.consultoria.enums

enum class EnumAtributosPesquisaEmpresa(
    val param: String,
    val atributo: String
) {
    NOME("nome", "nome_empresa"), ID("id", "id"), CNPJ("cnpj", "cnpj");

}
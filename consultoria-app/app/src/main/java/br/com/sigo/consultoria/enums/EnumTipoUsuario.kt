package br.com.sigo.consultoria.enums

enum class EnumTipoUsuario(val tipo: Int, val desc: String, val admin: Boolean) {

    USUARIO(1, "Usuário", false),
    CONSULTOR(2, "Consultor", false),
    ADMIN(3, "Administrador", true);

    companion object{
        fun retornaTipos(admin: Boolean): List<String>{

            return emptyList()
        }
    }
}
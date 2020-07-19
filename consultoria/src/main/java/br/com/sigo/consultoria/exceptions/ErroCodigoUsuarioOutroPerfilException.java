package br.com.sigo.consultoria.exceptions;

public class ErroCodigoUsuarioOutroPerfilException extends ConsultoriaException {
  public ErroCodigoUsuarioOutroPerfilException() {
    super("Este código de usuário já pertence à um perfil.");
  }
}

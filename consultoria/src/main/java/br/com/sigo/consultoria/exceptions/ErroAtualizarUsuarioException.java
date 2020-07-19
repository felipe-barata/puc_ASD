package br.com.sigo.consultoria.exceptions;

public class ErroAtualizarUsuarioException extends ConsultoriaException {
  public ErroAtualizarUsuarioException() {
    super("Erro salvar/atualizar usuário");
  }
}

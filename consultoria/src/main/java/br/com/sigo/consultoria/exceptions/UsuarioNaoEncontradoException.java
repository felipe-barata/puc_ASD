package br.com.sigo.consultoria.exceptions;

import java.text.MessageFormat;

public class UsuarioNaoEncontradoException extends ConsultoriaException {
  public UsuarioNaoEncontradoException(Integer codigoUsuario) {
    super(MessageFormat.format("Usuario: [ {0} ] não encontrado", codigoUsuario));
  }
}

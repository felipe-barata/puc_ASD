package br.com.sigo.consultoria.exceptions;

import java.text.MessageFormat;

public class CodigoCategoriaExistenteException extends ConsultoriaException {
  public CodigoCategoriaExistenteException(Integer categoria) {
    super(MessageFormat.format("Código de Categoria: [ {0} ] já existente", categoria));
  }
}

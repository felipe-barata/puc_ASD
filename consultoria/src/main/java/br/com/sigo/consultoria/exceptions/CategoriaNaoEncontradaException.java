package br.com.sigo.consultoria.exceptions;

import java.text.MessageFormat;

public class CategoriaNaoEncontradaException extends ConsultoriaException {
  public CategoriaNaoEncontradaException(Integer categoria) {
    super(MessageFormat.format("Categoria: [ {0} ] não encontrada", categoria));
  }
}

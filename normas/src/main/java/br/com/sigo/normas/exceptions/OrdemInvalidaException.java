package br.com.sigo.normas.exceptions;

import java.text.MessageFormat;

public class OrdemInvalidaException extends NormasException {

  public OrdemInvalidaException(String ordenacao) {
    super(MessageFormat.format("ORDENAÇÃO: [{0}] INVÁLIDA, SÃO ACEITAS APENAS: [ASC] ou [DESC]", ordenacao));
  }
}

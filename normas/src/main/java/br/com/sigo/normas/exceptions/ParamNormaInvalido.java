package br.com.sigo.normas.exceptions;

import java.text.MessageFormat;

public class ParamNormaInvalido extends NormasException {

  public ParamNormaInvalido(String param) {
    super(MessageFormat.format("PARÂMETRO: [{0}] INVÁLIDO, ACEITOS APENAS: [norma], [titulo]", param));
  }
}

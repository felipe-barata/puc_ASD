package br.com.sigo.consultoria.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConsultoriaException extends Exception {

  private String message;

}

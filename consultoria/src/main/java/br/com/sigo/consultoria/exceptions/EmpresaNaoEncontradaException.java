package br.com.sigo.consultoria.exceptions;

import java.text.MessageFormat;

public class EmpresaNaoEncontradaException extends ConsultoriaException {
  public EmpresaNaoEncontradaException(Integer codigoEmpresa) {
    super(MessageFormat.format("Empresa: [ {0} ] não encontrada", codigoEmpresa));
  }

  public EmpresaNaoEncontradaException(String cnpj) {
    super(MessageFormat.format("Empresa com CNPJ: [ {0} ] não encontrada", cnpj));
  }
}

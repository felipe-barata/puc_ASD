package br.com.sigo.consultoria.enums;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public enum EnumAtributosPesquisaUsuario implements Serializable {

  ID("id", "id", ""),
  NOME("nome", "nome", ""),
  EMPRESA("empresa", "empresa_id", "usuario_id"),
  ;

  private String param;
  private String atributo;
  private String atributoJoin;

  public String getParam() {
    return param;
  }

  public String getAtributo() {
    return atributo;
  }

  public String getAtributoJoin() {
    return atributoJoin;
  }
}

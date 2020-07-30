package br.com.sigo.consultoria.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumAtributosPesquisaEmpresa {

  @JsonProperty("nome")
  NOME("nome", "nome_empresa"),
  @JsonProperty("id")
  ID("id", "id"),
  @JsonProperty("cnpj")
  CNPJ("cnpj", "cnpj"),
  ;

  private String param;

  private String atributo;

  public String getParam() {
    return param;
  }

  public String getAtributo() {
    return atributo;
  }
}

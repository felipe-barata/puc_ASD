package br.com.sigo.normas.enums;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum NormasParamsEnum {

  TITULO("titulo"),

  NORMA("norma");

  private String param;

  public String getParam() {
    return param;
  }

  public static NormasParamsEnum get(String p) {
    return Stream.of(values()).filter(e -> e.getParam().equals(p)).findFirst().orElse(null);
  }
}

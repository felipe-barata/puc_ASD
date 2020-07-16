package br.com.sigo.consultoria.enums;

import java.util.stream.Stream;

public enum EnumSistemas {

  LOGISTICA(1, "Sistem de logística"),
  GESTAO_PROCESSOS(2, "Sistema de gestão de processos industriais"),
  MONITORAMENTO_VENDAS(3, "Monitoramento de vendas"),
  SEGURANCA_QUALIDADE(4, "Segurança e qualidade"),
  INTELIGENCIA_NEGOCIO(5, "Inteligência do negócio"),
  RELATORIOS_ACOMPANHAMENTO(6, "Relatórios de acompanhamento"),
  ;

  EnumSistemas(Integer codigo, String descricao) {
    this.codigo = codigo;
    this.descricao = descricao;
  }

  private Integer codigo;
  private String descricao;

  public Integer getCodigo() {
    return codigo;
  }

  public String getDescricao() {
    return descricao;
  }

  public Integer getId() {
    return codigo;
  }

  public static EnumSistemas getEnum(Integer id) {
    return Stream.of(values()).filter(e -> e.getCodigo().equals(id)).findFirst().orElse(null);
  }
}

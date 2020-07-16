package br.com.sigo.consultoria.domain;

import br.com.sigo.consultoria.enums.EnumSistemas;
import br.com.sigo.consultoria.enums.EnumSistemasMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "integracoes")
public class Integracoes implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Convert(converter = EnumSistemasMapping.class)
  private EnumSistemas sistema;

  private String chave;

  private String descricao;
}

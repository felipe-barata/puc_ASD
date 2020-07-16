package br.com.sigo.consultoria.domain.chave;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratoIntegracoesPK implements Serializable {

  @Column(name = "contrato_id")
  private Integer contratoId;

  @Column(name = "integracoes_id")
  private Long integracoesId;

}

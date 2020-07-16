package br.com.sigo.consultoria.domain;

import br.com.sigo.consultoria.domain.chave.ContratoIntegracoesPK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "integracoes")
public class ContratoIntegracoes implements Serializable {

  @EmbeddedId
  private ContratoIntegracoesPK chave;

  public Integer getContratoId() {
    return chave.getContratoId();
  }

  public Long getIntegracoesId() {
    return chave.getIntegracoesId();
  }
}

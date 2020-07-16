package br.com.sigo.consultoria.domain;

import br.com.sigo.consultoria.domain.chave.UsuarioEmpresaPK;
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
@Table(name = "usuario_empresa")
public class UsuarioEmpresa implements Serializable {

  @EmbeddedId
  private UsuarioEmpresaPK chave;

  public Integer getUsuarioId() {
    return chave.getUsuarioId();
  }

  public Integer getEmpresaId() {
    return chave.getEmpresaId();
  }
}

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
public class UsuarioEmpresaPK implements Serializable {

  @Column(name = "usuario_id")
  private Integer usuarioId;

  @Column(name = "empresa_id")
  private Integer empresaId;

}

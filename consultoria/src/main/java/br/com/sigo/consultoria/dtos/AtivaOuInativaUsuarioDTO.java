package br.com.sigo.consultoria.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AtivaOuInativaUsuarioDTO implements Serializable {

  @Min(value = 1, message = "Código do usuário é obrigatório")
  private Integer codigoUsuario;

  @NotNull(message = "Campo ativo é obrigatório")
  private Boolean ativo;

}

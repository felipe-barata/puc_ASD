package br.com.sigo.consultoria.dtos;

import br.com.sigo.consultoria.enums.EnumAtributosPesquisaUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ConsultarUsuarioDTO implements Serializable {

  @NotNull
  private EnumAtributosPesquisaUsuario atributo;

  @NotBlank
  private String valor;

}

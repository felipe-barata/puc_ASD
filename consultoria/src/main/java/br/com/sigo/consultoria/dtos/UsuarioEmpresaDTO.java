package br.com.sigo.consultoria.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioEmpresaDTO implements Serializable {

  @Min(value = 1, message = "ID do usuário deve ser informado")
  private int idUsuario;

  @Min(value = 1, message = "ID da empresa deve ser informado")
  private int idEmpresa;

}

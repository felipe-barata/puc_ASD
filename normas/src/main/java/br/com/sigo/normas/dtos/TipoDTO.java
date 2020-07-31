package br.com.sigo.normas.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TipoDTO implements Serializable {

  private int id;

  @NotBlank(message = "Descrição é obrigatória")
  private String descricao;
}

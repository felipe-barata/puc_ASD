package br.com.sigo.consultoria.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class CategoriaDTO implements Serializable {

  private int id;

  @NotBlank(message = "Descrição obrigatória")
  private String descricao;

}

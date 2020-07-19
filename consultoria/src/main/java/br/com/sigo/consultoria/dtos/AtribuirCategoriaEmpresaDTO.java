package br.com.sigo.consultoria.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AtribuirCategoriaEmpresaDTO implements Serializable {

  @NotBlank(message = "Obrigatório informar um CNPJ")
  private String cnpj;

  @Min(value = 1, message = "Obrigatório informar uma categoria")
  private Integer categoria;

}

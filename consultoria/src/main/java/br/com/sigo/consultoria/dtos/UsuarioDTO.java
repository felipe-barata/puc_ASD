package br.com.sigo.consultoria.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO implements Serializable {

  @Min(value = 1, message = "Código inválido")
  private int codigo;

  @NotBlank(message = "Senha não pode ser vazia")
  @Length(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
  private String senha;

  @NotBlank(message = "Nome não pode ser vazio")
  private String nome;

}

package br.com.sigo.consultoria.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtAuthenticationDto {

  @Min(value = 1, message = "Código inválido")
  private Integer codigo;

  @NotBlank(message = "Senha não pode ser vazia")
  @Length(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
  private String senha;

  @Override
  public String toString() {
    return "JwtAuthenticationRequestDto [codigo=" + codigo + ", senha=" + senha + "]";
  }

}

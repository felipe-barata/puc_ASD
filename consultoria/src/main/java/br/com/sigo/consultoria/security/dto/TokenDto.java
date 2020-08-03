package br.com.sigo.consultoria.security.dto;

import br.com.sigo.consultoria.enums.PerfilEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDto {

  private String token;

  private String username;

  private String displayName;

  private List<PerfilEnum> perfis;
}

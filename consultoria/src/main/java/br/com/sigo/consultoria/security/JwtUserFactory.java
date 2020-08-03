package br.com.sigo.consultoria.security;

import br.com.sigo.consultoria.domain.Usuario;
import br.com.sigo.consultoria.enums.PerfilEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class JwtUserFactory {

  private JwtUserFactory() {
  }

  /**
   * Converte e gera um JwtUser com base nos dados de um funcionário.
   *
   * @param usuario
   * @return JwtUser
   */
  public static JwtUser create(Usuario usuario, List<PerfilEnum> perfis) {
    return new JwtUser(Long.valueOf(usuario.getId()), String.valueOf(usuario.getCodigo()), usuario.getSenha(), usuario.getNome(),
        mapToGrantedAuthorities(perfis));
  }

  /**
   * Converte o perfil do usuário para o formato utilizado pelo Spring Security.
   *
   * @param perfis
   * @return List<GrantedAuthority>
   */
  private static List<GrantedAuthority> mapToGrantedAuthorities(List<PerfilEnum> perfis) {
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    for (PerfilEnum perfilEnum : perfis) {
      authorities.add(new SimpleGrantedAuthority(perfilEnum.toString()));
    }
    return authorities;
  }

}

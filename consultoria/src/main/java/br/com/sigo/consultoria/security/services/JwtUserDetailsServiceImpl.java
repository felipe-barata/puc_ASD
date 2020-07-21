package br.com.sigo.consultoria.security.services;

import br.com.sigo.consultoria.domain.Perfis;
import br.com.sigo.consultoria.domain.Usuario;
import br.com.sigo.consultoria.enums.PerfilEnum;
import br.com.sigo.consultoria.repository.PerfisRepository;
import br.com.sigo.consultoria.security.JwtUserFactory;
import br.com.sigo.consultoria.services.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtUserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  private PerfisRepository perfisRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Usuario> usuario = usuarioService.buscarUsername(username);

    if (usuario.isPresent()) {
      Usuario u = usuario.get();
      if (!u.getAtivo()) {
        log.warn("loadUserByUsername - usuario inativo");
        throw new UsernameNotFoundException("Usuário inativo");
      }

      List<PerfilEnum> collect = perfisRepository.retornaPerfisUsuario(u.getId()).stream().map(Perfis::getPerfil).collect(Collectors.toList());

      return JwtUserFactory.create(u, collect);
    }

    throw new UsernameNotFoundException("Código não encontrado.");
  }

}

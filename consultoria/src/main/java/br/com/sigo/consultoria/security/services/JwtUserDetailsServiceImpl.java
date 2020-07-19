package br.com.sigo.consultoria.security.services;

import br.com.sigo.consultoria.domain.Perfis;
import br.com.sigo.consultoria.domain.Usuario;
import br.com.sigo.consultoria.enums.PerfilEnum;
import br.com.sigo.consultoria.repository.PerfisRepository;
import br.com.sigo.consultoria.security.JwtUserFactory;
import br.com.sigo.consultoria.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
      List<PerfilEnum> collect = perfisRepository.retornaPerfisUsuario(u.getId()).stream().map(Perfis::getPerfil).collect(Collectors.toList());

      return JwtUserFactory.create(u, collect);
    }

    throw new UsernameNotFoundException("Email não encontrado.");
  }

}

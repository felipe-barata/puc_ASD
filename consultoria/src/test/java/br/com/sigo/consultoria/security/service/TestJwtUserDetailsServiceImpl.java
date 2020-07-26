package br.com.sigo.consultoria.security.service;

import br.com.sigo.consultoria.domain.Perfis;
import br.com.sigo.consultoria.domain.Usuario;
import br.com.sigo.consultoria.enums.PerfilEnum;
import br.com.sigo.consultoria.repository.PerfisRepository;
import br.com.sigo.consultoria.security.services.JwtUserDetailsServiceImpl;
import br.com.sigo.consultoria.services.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestJwtUserDetailsServiceImpl {

  private static final Integer CODIGO = 1;
  private static final String NOME = "TESTE";
  private static final String SENHA = "123456";
  private static final Integer ID_USER = 1;
  @MockBean
  private UsuarioService usuarioService;

  @MockBean
  private PerfisRepository perfisRepository;

  @Autowired
  private JwtUserDetailsServiceImpl jwtUserDetailsService;

  @Test
  public void testLoadUserByUsername() {
    BDDMockito.given(usuarioService.buscarUsername(Mockito.anyString())).willReturn(Optional.of(getUsuario(true)));
    BDDMockito.given(perfisRepository.retornaPerfisUsuario(Mockito.anyInt())).willReturn(getPerfis());

    Assertions.assertNotNull(jwtUserDetailsService.loadUserByUsername("1"));
  }

  @Test()
  public void testLoadUserByUsernameInativo() {
    BDDMockito.given(usuarioService.buscarUsername(Mockito.anyString())).willReturn(Optional.of(getUsuario(false)));
    BDDMockito.given(perfisRepository.retornaPerfisUsuario(Mockito.anyInt())).willReturn(getPerfis());

    Assertions.assertThrows(UsernameNotFoundException.class, () -> jwtUserDetailsService.loadUserByUsername("1"));
  }

  @Test()
  public void testLoadUserByUsernameNaoEncontrado() {
    BDDMockito.given(usuarioService.buscarUsername(Mockito.anyString())).willReturn(Optional.empty());
    BDDMockito.given(perfisRepository.retornaPerfisUsuario(Mockito.anyInt())).willReturn(getPerfis());

    Assertions.assertThrows(UsernameNotFoundException.class, () -> jwtUserDetailsService.loadUserByUsername("1"));
  }

  private List<Perfis> getPerfis() {
    return Stream.of(PerfilEnum.values()).map(p -> new Perfis(1L, p, getUsuario(true))).collect(Collectors.toUnmodifiableList());
  }

  private Usuario getUsuario(boolean ativo) {
    return Usuario.builder()
        .codigo(CODIGO)
        .nome(NOME)
        .senha(SENHA)
        .ativo(ativo)
        .id(ID_USER)
        .build();
  }

}

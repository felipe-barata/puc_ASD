package br.com.sigo.consultoria.services;

import br.com.sigo.consultoria.domain.Usuario;
import br.com.sigo.consultoria.dtos.UsuarioDTO;
import br.com.sigo.consultoria.enums.PerfilEnum;
import br.com.sigo.consultoria.exceptions.ConsultoriaException;
import br.com.sigo.consultoria.repository.PerfisRepository;
import br.com.sigo.consultoria.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestUsuarioService {

  private static final String NOME = "Teste usuario";
  private static final String SENHA = "123456";
  @MockBean
  private UsuarioRepository usuarioRepository;

  @MockBean
  private PerfisRepository perfisRepository;

  @Autowired
  private UsuarioService usuarioService;

  @Test
  public void testBuscarUsernameEncontraUsuario() {
    BDDMockito.when(usuarioRepository.findByCodigo(1)).thenReturn(Optional.of(new Usuario()));
    Optional<Usuario> usuario = usuarioService.buscarUsername("1");
    Assertions.assertTrue(usuario.isPresent());
  }

  @Test
  public void testBuscarUsernameNaoEncontraUsuario() {
    BDDMockito.when(usuarioRepository.findByCodigo(2)).thenReturn(Optional.empty());
    Optional<Usuario> usuario = usuarioService.buscarUsername("2");
    Assertions.assertTrue(usuario.isEmpty());
  }

  @Test
  public void testAtualizarInserirNovo() throws ConsultoriaException {
    BDDMockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(Usuario.builder().id(2).build());
    UsuarioDTO dto = usuarioService.atualizar(getUsuarioDTO(2), getListaPerfis());
    Assertions.assertNotNull(dto);
  }

  @Test
  public void testAtualizarUsuarioExiste() throws ConsultoriaException {
    BDDMockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(Usuario.builder().id(1).build());
    UsuarioDTO dto = usuarioService.atualizar(getUsuarioDTO(1), getListaPerfis());
    Assertions.assertNotNull(dto);
  }

  @Test
  public void testAtivaOuInativaUsuarioAtiva() throws ConsultoriaException {
    BDDMockito.when(usuarioRepository.findByCodigo(1)).thenReturn(Optional.of(new Usuario()));
    BDDMockito.when(usuarioRepository.ativaInativaUsuario(1, true)).thenReturn(1);
    boolean b = usuarioService.ativaOuInativaUsuario(1, false);
    Assertions.assertTrue(b);
  }

  @Test
  public void testAtivaOuInativaUsuarioDesativa() throws ConsultoriaException {
    BDDMockito.when(usuarioRepository.findByCodigo(1)).thenReturn(Optional.of(new Usuario()));
    BDDMockito.when(usuarioRepository.ativaInativaUsuario(1, false)).thenReturn(1);
    boolean b = usuarioService.ativaOuInativaUsuario(1, true);
    Assertions.assertTrue(b);
  }

  private List<PerfilEnum> getListaPerfis() {
    return Stream.of(PerfilEnum.values()).collect(Collectors.toList());
  }

  private UsuarioDTO getUsuarioDTO(int codigo) {
    return UsuarioDTO.builder()
        .codigo(codigo)
        .nome(NOME)
        .senha(SENHA)
        .build();
  }
}

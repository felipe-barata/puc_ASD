package br.com.sigo.consultoria.repository;

import br.com.sigo.consultoria.domain.Usuario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestUsuarioRepository {

  private static final Boolean ATIVO = false;
  private static final String SENHA = "123456";
  private static final String NOME = "User Teste";
  private static final Integer CODIGO = 1;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @BeforeAll
  public void setUp() {
    usuarioRepository.save(getUsuario());
  }

  @Test
  public void testFindByCodigoUsuarioExiste() {
    Optional<Usuario> byCodigo = usuarioRepository.findByCodigo(CODIGO);
    Assertions.assertTrue(byCodigo.isPresent());
  }

  @Test
  public void testFindByCodigoUsuarioNaoExiste() {
    Optional<Usuario> byCodigo = usuarioRepository.findByCodigo(2);
    Assertions.assertTrue(byCodigo.isEmpty());
  }

  @Test
  public void testAtivaInativaUsuario() {
    Usuario u = getUsuario();
    Boolean aBoolean = usuarioRepository.ativaInativaUsuario(CODIGO, !u.getAtivo()) > 0;
    Assertions.assertTrue(aBoolean);
  }

  @AfterAll
  public final void tearDown() {
    usuarioRepository.deleteAll();
  }

  private Usuario getUsuario() {
    return Usuario.builder()
        .ativo(ATIVO)
        .senha(SENHA)
        .nome(NOME)
        .codigo(CODIGO)
        .build();
  }

}

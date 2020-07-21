package br.com.sigo.consultoria.repository;

import br.com.sigo.consultoria.domain.Perfis;
import br.com.sigo.consultoria.domain.Usuario;
import br.com.sigo.consultoria.enums.PerfilEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestPerfisRepository {

  private static final Boolean ATIVO = false;
  private static final String SENHA = "123456";
  private static final String NOME = "User Teste";
  private static final Integer CODIGO_1 = 1;
  private static final Integer CODIGO_2 = 2;

  @Autowired
  private PerfisRepository perfisRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Test
  public void testRetornaPerfisUsuario() {
    Usuario u = getUsuario(CODIGO_1);
    usuarioRepository.save(u);
    perfisRepository.save(getPerfil(u, PerfilEnum.ROLE_USUARIO));
    perfisRepository.save(getPerfil(u, PerfilEnum.ROLE_ADMIN));
    List<Perfis> perfis = perfisRepository.retornaPerfisUsuario(CODIGO_1);
    Assertions.assertFalse(perfis.isEmpty());
  }

  @Test
  public void testApagaPerfilUsuario() {
    Usuario u2 = getUsuario(CODIGO_2);
    usuarioRepository.save(u2);
    perfisRepository.save(getPerfil(u2, PerfilEnum.ROLE_USUARIO));
    perfisRepository.save(getPerfil(u2, PerfilEnum.ROLE_ADMIN));
    perfisRepository.apagaPerfilUsuario(CODIGO_2);
    Assertions.assertTrue(perfisRepository.findAll().isEmpty());
  }

  @AfterEach
  public final void tearDown() {
    perfisRepository.deleteAll();
    usuarioRepository.deleteAll();
  }

  private Perfis getPerfil(Usuario usuario, PerfilEnum perfilEnum) {
    return Perfis.builder()
        .usuario(usuario)
        .perfil(perfilEnum)
        .build();
  }

  private Usuario getUsuario(Integer codigo) {
    return Usuario.builder()
        .ativo(ATIVO)
        .senha(SENHA)
        .nome(NOME)
        .codigo(codigo)
        .build();
  }

}

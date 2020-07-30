package br.com.sigo.consultoria.repository;

import br.com.sigo.consultoria.domain.Categoria;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCategoriaRepository {

  @Autowired
  private CategoriaRepository categoriaRepository;

  @BeforeAll
  public void setUp() {
    categoriaRepository.save(getCategoria());
    categoriaRepository.save(getCategoria());
  }

  @Test
  public void testSelectTodasNormas() {
    PageRequest pageRequest = PageRequest.of(0, 25);
    Page<Categoria> normas = this.categoriaRepository.findAll(pageRequest);
    Assertions.assertEquals(2, normas.getContent().size());
  }

  @AfterAll
  public final void tearDown() {
    categoriaRepository.deleteAll();
  }

  private Categoria getCategoria() {
    return Categoria.builder()
        .descricao("teste").build();
  }
}

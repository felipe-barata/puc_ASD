package br.com.sigo.normas.repository;

import br.com.sigo.normas.domain.CategoriaNorma;
import br.com.sigo.normas.domain.Norma;
import br.com.sigo.normas.domain.TipoNorma;
import br.com.sigo.normas.projections.NormaProjection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestNormaRepository {

  private static final String NORMA = "teste de inserção de norma";
  private static final String TITULO = "Título";
  private static final String CATEGORIA = "Categoria";
  private static final String TIPO = "Tipo";

  @Autowired
  private CategoriaNormaRepository categoriaNormaRepository;

  @Autowired
  private TipoNormaRepository tipoNormaRepository;

  @Autowired
  private NormaRepository normaRepository;

  @BeforeAll
  public void setUp() {
    CategoriaNorma categoriaNorma = categoriaNormaRepository.save(getCategoria());
    TipoNorma tipoNorma = tipoNormaRepository.save(getTipo());
    normaRepository.save(getNorma(categoriaNorma, tipoNorma));
  }

  @Test
  public void testSelectTodasNormas() {
    PageRequest pageRequest = PageRequest.of(0, 1, Sort.Direction.ASC, "norma");
    Page<NormaProjection> normas = this.normaRepository.selectTodasNormas(pageRequest);
    Assertions.assertEquals(1, normas.getSize());
  }

  @AfterAll
  public final void tearDown() {
    normaRepository.deleteAll();
    categoriaNormaRepository.deleteAll();
    tipoNormaRepository.deleteAll();
  }

  private Norma getNorma(CategoriaNorma categoria, TipoNorma tipo) {
    return Norma.builder()
        .categoriaNorma(categoria)
        .documentos(new ArrayList<>())
        .norma(NORMA)
        .tipoNorma(tipo)
        .titulo(TITULO)
        .build();
  }

  private CategoriaNorma getCategoria() {
    return CategoriaNorma.builder().descricao(CATEGORIA).build();
  }

  private TipoNorma getTipo() {
    return TipoNorma.builder().descricao(TIPO).build();
  }
}

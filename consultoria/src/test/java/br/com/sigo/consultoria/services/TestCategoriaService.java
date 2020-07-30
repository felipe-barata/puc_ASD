package br.com.sigo.consultoria.services;

import br.com.sigo.consultoria.domain.Categoria;
import br.com.sigo.consultoria.dtos.CategoriaDTO;
import br.com.sigo.consultoria.exceptions.ConsultoriaException;
import br.com.sigo.consultoria.repository.CategoriaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCategoriaService {

  private static final String DESCRICAO = "Teste categoria";

  @MockBean
  private CategoriaRepository categoriaRepository;

  @Autowired
  private CategoriaService categoriaService;

  @BeforeAll
  public void setUp() throws Exception {
    BDDMockito.when(this.categoriaRepository.save(Mockito.any(Categoria.class))).thenReturn(Categoria.builder().id(1).build());
  }

  @Test
  public void testAtualizaCategoriaNaoExiste() throws ConsultoriaException {
    CategoriaDTO categoriaDTO = categoriaService.atualizaCategoria(getDTO());
    Assertions.assertTrue(categoriaDTO.getId() > 0);
  }

  @Test
  public void testAtualizaCategoriaExiste() throws ConsultoriaException {
    BDDMockito.when(this.categoriaRepository.findById(1)).thenReturn(Optional.of(Categoria.builder().id(1).build()));
    CategoriaDTO categoriaDTO = categoriaService.atualizaCategoria(getDTO());
    Assertions.assertTrue(categoriaDTO.getId() > 0);
  }

  @Test
  public void testRetornarTodasCategorias() {
    List<Categoria> c = new ArrayList<>();
    c.add(new Categoria());
    Page<Categoria> pages = new PageImpl<>(c);
    PageRequest pageRequest = PageRequest.of(1, 1);
    BDDMockito.when(categoriaRepository.findAll(pageRequest)).thenReturn(pages);

    Assertions.assertFalse(categoriaService.retornarTodasCategorias(PageRequest.of(1, 1)).isEmpty());
  }

  private CategoriaDTO getDTO() {
    return CategoriaDTO.builder()
        .id(1)
        .descricao(DESCRICAO)
        .build();
  }
}

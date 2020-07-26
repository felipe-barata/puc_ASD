package br.com.sigo.normas.services;

import br.com.sigo.normas.exceptions.NormasException;
import br.com.sigo.normas.exceptions.OrdemInvalidaException;
import br.com.sigo.normas.exceptions.ParamNormaInvalido;
import br.com.sigo.normas.projections.NormaProjection;
import br.com.sigo.normas.repository.NormaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestNormaService {

  private static final String PARAM = "norma";
  private static final String SORT = "ASC";
  private static final int SIZE = 1;
  private static final int PAGE = 0;
  @Autowired
  private NormaService normaService;

  @MockBean
  private NormaRepository normaRepository;

  @Test
  public void testRetornarTodasNormas() throws NormasException {
    List<NormaProjection> c = new ArrayList<>();
    c.add(new NormaProjection() {
      @Override
      public String getCategoria() {
        return "teste";
      }

      @Override
      public String getTipo() {
        return "teste";
      }

      @Override
      public String getTitulo() {
        return "teste";
      }

      @Override
      public String getNorma() {
        return "teste";
      }
    });
    Page<NormaProjection> pages = new PageImpl<>(c);
    Sort.Direction direction = Sort.Direction.fromString(SORT);
    PageRequest pageRequest = PageRequest.of(PAGE, SIZE, direction, PARAM);
    BDDMockito.when(normaRepository.selectTodasNormas(pageRequest)).thenReturn(pages);

    Assertions.assertFalse(normaService.retornarTodasNormas(PAGE, SIZE, SORT, PARAM).isEmpty());

  }

  @Test
  public void testRetornarTodasNormasOrdemInvalida() throws NormasException {
    Assertions.assertThrows(OrdemInvalidaException.class, () -> normaService.retornarTodasNormas(PAGE, SIZE, "Teste", PARAM).isEmpty());
  }

  @Test
  public void testRetornarTodasNormasParamInvalido() throws NormasException {
    Assertions.assertThrows(ParamNormaInvalido.class, () -> normaService.retornarTodasNormas(PAGE, SIZE, SORT, "teste").isEmpty());
  }

}

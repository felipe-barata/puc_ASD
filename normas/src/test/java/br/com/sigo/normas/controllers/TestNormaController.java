package br.com.sigo.normas.controllers;

import br.com.sigo.normas.dtos.ConsultaNormaDTO;
import br.com.sigo.normas.exceptions.OrdemInvalidaException;
import br.com.sigo.normas.exceptions.ParamNormaInvalido;
import br.com.sigo.normas.projections.NormaProjection;
import br.com.sigo.normas.services.NormaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TestNormaController {

  private static final String URL = "/api/norma/consultarNormas";

  private static final int SIZE = 1;
  private static final String ORDENACAO = "DESC";
  private static final int PAGE = 0;
  private static final String PARAM = "titulo";
  private static final String CATEGORIA = "Categoria";
  private static final String TIPO = "Tipo";
  private static final String TITULO = "Titulo da norma";
  private static final String NORMA = "Texto da norma";
  @Autowired
  private MockMvc mvc;

  @MockBean
  private NormaService normaService;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  public void testConsultarNormas() throws Exception {
    BDDMockito.given(normaService.retornarTodasNormas(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).willReturn(retornaPage());

    mvc.perform(MockMvcRequestBuilders.post(URL)
        .content(objectMapper.writeValueAsString(getDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void testConsultarNormasSemOrdenacao() throws Exception {
    BDDMockito.given(normaService.retornarTodasNormas(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).willReturn(retornaPage());

    ConsultaNormaDTO dto = getDTO();
    dto.setOrdenacao(null);
    mvc.perform(MockMvcRequestBuilders.post(URL)
        .content(objectMapper.writeValueAsString(dto))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void testConsultarNormasSemParam() throws Exception {
    BDDMockito.given(normaService.retornarTodasNormas(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).willReturn(retornaPage());

    ConsultaNormaDTO dto = getDTO();
    dto.setParam(null);
    mvc.perform(MockMvcRequestBuilders.post(URL)
        .content(objectMapper.writeValueAsString(dto))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void testConsultarNormasOrdenacaoInvalida() throws Exception {
    ConsultaNormaDTO dto = getDTO();
    dto.setOrdenacao("asc");
    BDDMockito.given(normaService.retornarTodasNormas(dto.getPage(), dto.getSize(), dto.getOrdenacao(), dto.getParam())).willThrow(OrdemInvalidaException.class);
    mvc.perform(MockMvcRequestBuilders.post(URL)
        .content(objectMapper.writeValueAsString(dto))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testConsultarNormasParamInvalido() throws Exception {
    ConsultaNormaDTO dto = getDTO();
    dto.setParam("tetse");
    BDDMockito.given(normaService.retornarTodasNormas(dto.getPage(), dto.getSize(), dto.getOrdenacao(), dto.getParam())).willThrow(ParamNormaInvalido.class);
    mvc.perform(MockMvcRequestBuilders.post(URL)
        .content(objectMapper.writeValueAsString(dto))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testConsultarNormasSemConteudo() throws Exception {
    BDDMockito.given(normaService.retornarTodasNormas(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).willReturn(Page.empty());

    ConsultaNormaDTO dto = getDTO();
    dto.setOrdenacao(null);
    mvc.perform(MockMvcRequestBuilders.post(URL)
        .content(objectMapper.writeValueAsString(dto))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  private Page<NormaProjection> retornaPage() {
    List<NormaProjection> c = new ArrayList<>();
    c.add(getNorma());
    return new PageImpl<>(c);
  }

  private ConsultaNormaDTO getDTO() {
    return ConsultaNormaDTO.builder()
        .ordenacao(ORDENACAO)
        .page(PAGE)
        .param(PARAM)
        .size(SIZE)
        .build();
  }

  private NormaProjection getNorma() {
    return new NormaProjection() {
      @Override
      public String getCategoria() {
        return CATEGORIA;
      }

      @Override
      public String getTipo() {
        return TIPO;
      }

      @Override
      public String getTitulo() {
        return TITULO;
      }

      @Override
      public String getNorma() {
        return NORMA;
      }

      @Override
      public Integer getId() {
        return 1;
      }
    };
  }
}

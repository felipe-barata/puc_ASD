package br.com.sigo.consultoria.controllers;

import br.com.sigo.consultoria.domain.Categoria;
import br.com.sigo.consultoria.dtos.CategoriaDTO;
import br.com.sigo.consultoria.dtos.PaginacaoDTO;
import br.com.sigo.consultoria.services.CategoriaService;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TestCategoriaController {

  private static final String URL = "/api/categoria";
  private static final String LISTAR = "/api/categoria/listar";
  private static final String DESC = "Teste";
  private static final int CAT = 1;
  private static final int SIZE = 1;
  private static final int PAGE = 0;

  @MockBean
  private CategoriaService categoriaService;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @WithMockUser(roles = {"USUARIO"})
  public void testAtualizarCategoriaVaiPassar() throws Exception {
    BDDMockito.given(this.categoriaService.atualizaCategoria(Mockito.any(CategoriaDTO.class))).willReturn(getDTO(DESC, CAT));

    mvc.perform(MockMvcRequestBuilders.post(URL)
        .content(objectMapper.writeValueAsString(getDTO(DESC, 0)))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = {"USUARIO"})
  public void testAtualizarCategoriaErroValidacao() throws Exception {
    BDDMockito.given(this.categoriaService.atualizaCategoria(Mockito.any(CategoriaDTO.class))).willReturn(getDTO(DESC, CAT));

    mvc.perform(MockMvcRequestBuilders.post(URL)
        .content(objectMapper.writeValueAsString(getDTO("", 0)))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser
  public void testAtualizarCategoriaAcessoNegado() throws Exception {
    BDDMockito.given(this.categoriaService.atualizaCategoria(Mockito.any(CategoriaDTO.class))).willReturn(getDTO(DESC, CAT));

    mvc.perform(MockMvcRequestBuilders.post(URL)
        .content(objectMapper.writeValueAsString(getDTO("", 0)))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  public void testAtualizarCategoriaNaoAutorizado() throws Exception {
    BDDMockito.given(this.categoriaService.atualizaCategoria(Mockito.any(CategoriaDTO.class))).willReturn(getDTO(DESC, CAT));

    mvc.perform(MockMvcRequestBuilders.post(URL)
        .content(objectMapper.writeValueAsString(getDTO("", 0)))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void testRetornaTodasCategoriasNaoAutorizado() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post(LISTAR)
        .content(objectMapper.writeValueAsString(getPageable()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser
  public void testRetornaTodasCategoriasAcessoNegado() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post(LISTAR)
        .content(objectMapper.writeValueAsString(getPageable()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = {"USUARIO"})
  public void testRetornaTodasCategorias() throws Exception {
    BDDMockito.given(categoriaService.retornarTodasCategorias(PageRequest.of(getPageable().getPagina(), getPageable().getQtdRegistros())))
        .willReturn(retornaPage());

    mvc.perform(MockMvcRequestBuilders.post(LISTAR)
        .content(objectMapper.writeValueAsString(getPageable()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = {"USUARIO"})
  public void testRetornaTodasCategoriasVazio() throws Exception {
    BDDMockito.given(categoriaService.retornarTodasCategorias(PageRequest.of(getPageable().getPagina(), getPageable().getQtdRegistros())))
        .willReturn(Page.empty());

    mvc.perform(MockMvcRequestBuilders.post(LISTAR)
        .content(objectMapper.writeValueAsString(getPageable()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  private CategoriaDTO getDTO(String desc, int cat) {
    return CategoriaDTO.builder().id(cat).descricao(desc).build();
  }

  private PaginacaoDTO getPageable() {
    return PaginacaoDTO.builder()
        .pagina(PAGE)
        .qtdRegistros(SIZE)
        .build();
  }

  private Page<Categoria> retornaPage() {
    List<Categoria> c = new ArrayList<>();
    c.add(Categoria.builder().descricao(DESC).id(1).build());
    return new PageImpl<>(c);
  }
}

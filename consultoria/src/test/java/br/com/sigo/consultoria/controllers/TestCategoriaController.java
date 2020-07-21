package br.com.sigo.consultoria.controllers;

import br.com.sigo.consultoria.dtos.CategoriaDTO;
import br.com.sigo.consultoria.services.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TestCategoriaController {

  private static final String URL = "/api/categoria";
  private static final String DESC = "Teste";
  private static final int CAT = 1;

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

  private CategoriaDTO getDTO(String desc, int cat) {
    return CategoriaDTO.builder().id(cat).descricao(desc).build();
  }
}

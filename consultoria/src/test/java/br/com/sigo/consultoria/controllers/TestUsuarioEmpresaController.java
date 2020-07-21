package br.com.sigo.consultoria.controllers;

import br.com.sigo.consultoria.dtos.UsuarioEmpresaDTO;
import br.com.sigo.consultoria.services.UsuarioEmpresaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class TestUsuarioEmpresaController {

  private static final String URL = "/api/usuarioEmpresa";

  @MockBean
  private UsuarioEmpresaService usuarioEmpresaService;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testAtribuiUsuarioEmpresaNaoAutorizado() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post(URL)
        .content(objectMapper.writeValueAsString(getLista()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser
  public void testAtribuiUsuarioEmpresaNegado() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post(URL)
        .content(objectMapper.writeValueAsString(getLista()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "USUARIO")
  public void testAtribuiUsuarioErro() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post(URL)
        .content(objectMapper.writeValueAsString(new ArrayList<>()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError());
  }

  @Test
  @WithMockUser(roles = "USUARIO")
  public void testAtribuiUsuarioErroValidacao() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post(URL)
        .content(objectMapper.writeValueAsString(new ArrayList<>()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  private List<UsuarioEmpresaDTO> getLista() {
    List<UsuarioEmpresaDTO> l = new ArrayList<>();
    l.add(UsuarioEmpresaDTO.builder().idEmpresa(1).idUsuario(1).build());
    l.add(UsuarioEmpresaDTO.builder().idEmpresa(1).idUsuario(2).build());
    return l;
  }
}

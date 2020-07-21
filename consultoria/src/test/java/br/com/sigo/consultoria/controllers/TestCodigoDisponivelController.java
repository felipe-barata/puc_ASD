package br.com.sigo.consultoria.controllers;

import br.com.sigo.consultoria.domain.Usuario;
import br.com.sigo.consultoria.services.UsuarioService;
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

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TestCodigoDisponivelController {

  private static final String URL = "/api/codigoDisponivel";

  @MockBean
  private UsuarioService usuarioService;

  @Autowired
  private MockMvc mvc;

  @Test
  @WithMockUser
  public void testVerificaCodigoUsuarioVaiRetornarIndisponivel() throws Exception {
    BDDMockito.when(usuarioService.buscarUsername(Mockito.anyString())).thenReturn(Optional.of(new Usuario()));
    mvc.perform(MockMvcRequestBuilders.get(URL).contentType(MediaType.APPLICATION_JSON).param("codigo", "1"))
        .andExpect(status().isOk());

  }

  @Test
  @WithMockUser
  public void testVerificaCodigoUsuarioVaiRetornarDisponivel() throws Exception {
    BDDMockito.when(usuarioService.buscarUsername(Mockito.anyString())).thenReturn(Optional.empty());
    mvc.perform(MockMvcRequestBuilders.get(URL).contentType(MediaType.APPLICATION_JSON).param("codigo", "1"))
        .andExpect(status().isNoContent());

  }

  @Test
  public void testVerificaCodigoUsuarioVaiRetornarNaoAutorizado() throws Exception {
    BDDMockito.when(usuarioService.buscarUsername(Mockito.anyString())).thenReturn(Optional.empty());
    mvc.perform(MockMvcRequestBuilders.get(URL).contentType(MediaType.APPLICATION_JSON).param("codigo", "1"))
        .andExpect(status().isUnauthorized());

  }

}

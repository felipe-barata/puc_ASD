package br.com.sigo.consultoria.controllers;

import br.com.sigo.consultoria.dtos.AtivaOuInativaUsuarioDTO;
import br.com.sigo.consultoria.dtos.UsuarioDTO;
import br.com.sigo.consultoria.services.UsuarioService;
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
public class TestUsuarioController {

  private static final String URL_ATUALIZA_USUARIO = "/api/usuario";
  private static final String URL_ATUALIZA_CONSULTOR = "/api/usuario/consultor";
  private static final String URL_ATUALIZA_ADMIN = "/api/usuario/admin";
  private static final String URL_ATIVA_USUARIO = "/api/usuario/admin/ativaOuInativa";
  private static final String URL_ATIVA_CONSULTOR = "/api/usuario/ativaOuInativa";
  private static final String SENHA = "123456";
  private static final String NOME = "Usuario";
  private static final int CODIGO = 1;

  @MockBean
  private UsuarioService usuarioService;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  //Testes usuário

  @Test
  public void testAtualizaUsuarioSistemaNaoAutorizado() throws Exception {

    mvc.perform(MockMvcRequestBuilders.post(URL_ATUALIZA_USUARIO)
        .content(objectMapper.writeValueAsString(new UsuarioDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());

  }

  @Test
  @WithMockUser
  public void testAtualizaUsuarioSistemaAcessoNegado() throws Exception {

    mvc.perform(MockMvcRequestBuilders.post(URL_ATUALIZA_USUARIO)
        .content(objectMapper.writeValueAsString(new UsuarioDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());

  }

  @Test
  @WithMockUser(roles = {"USUARIO"})
  public void testAtualizaUsuarioSistemaErroValidacao() throws Exception {

    mvc.perform(MockMvcRequestBuilders.post(URL_ATUALIZA_USUARIO)
        .content(objectMapper.writeValueAsString(new UsuarioDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

  }

  @Test
  @WithMockUser(roles = {"USUARIO"})
  public void testAtualizaUsuarioSistemaVaiPassar() throws Exception {
    UsuarioDTO dto = getDto();
    dto.setId(1);
    BDDMockito.given(usuarioService.atualizar(Mockito.any(UsuarioDTO.class), Mockito.anyList())).willReturn(dto);
    mvc.perform(MockMvcRequestBuilders.post(URL_ATUALIZA_USUARIO)
        .content(objectMapper.writeValueAsString(getDto()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

  }

  //Testes consultor

  @Test
  public void testAtualizaConsultorNaoAutorizado() throws Exception {

    mvc.perform(MockMvcRequestBuilders.post(URL_ATUALIZA_CONSULTOR)
        .content(objectMapper.writeValueAsString(new UsuarioDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());

  }

  @Test
  @WithMockUser
  public void testAtualizaConsultorAcessoNegado() throws Exception {

    mvc.perform(MockMvcRequestBuilders.post(URL_ATUALIZA_CONSULTOR)
        .content(objectMapper.writeValueAsString(new UsuarioDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());

  }

  @Test
  @WithMockUser(roles = {"USUARIO"})
  public void testAtualizaConsultorErroValidacao() throws Exception {

    mvc.perform(MockMvcRequestBuilders.post(URL_ATUALIZA_CONSULTOR)
        .content(objectMapper.writeValueAsString(new UsuarioDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

  }

  @Test
  @WithMockUser(roles = {"USUARIO"})
  public void testAtualizaConsultorVaiPassar() throws Exception {
    UsuarioDTO dto = getDto();
    dto.setId(1);
    BDDMockito.given(usuarioService.atualizar(Mockito.any(UsuarioDTO.class), Mockito.anyList())).willReturn(dto);
    mvc.perform(MockMvcRequestBuilders.post(URL_ATUALIZA_CONSULTOR)
        .content(objectMapper.writeValueAsString(getDto()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

  }


  //Testes admin

  @Test
  public void testAtualizaAdminNaoAutorizado() throws Exception {

    mvc.perform(MockMvcRequestBuilders.post(URL_ATUALIZA_ADMIN)
        .content(objectMapper.writeValueAsString(new UsuarioDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());

  }

  @Test
  @WithMockUser(roles = {"USUARIO"})
  public void testAtualizaAdminAcessoNegado() throws Exception {

    mvc.perform(MockMvcRequestBuilders.post(URL_ATUALIZA_ADMIN)
        .content(objectMapper.writeValueAsString(new UsuarioDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());

  }

  @Test
  @WithMockUser(roles = {"ADMIN"})
  public void testAtualizaAdminErroValidacao() throws Exception {

    mvc.perform(MockMvcRequestBuilders.post(URL_ATUALIZA_ADMIN)
        .content(objectMapper.writeValueAsString(new UsuarioDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

  }

  @Test
  @WithMockUser(roles = {"ADMIN"})
  public void testAtualizaAdminVaiPassar() throws Exception {
    UsuarioDTO dto = getDto();
    dto.setId(1);
    BDDMockito.given(usuarioService.atualizar(Mockito.any(UsuarioDTO.class), Mockito.anyList())).willReturn(dto);
    mvc.perform(MockMvcRequestBuilders.post(URL_ATUALIZA_ADMIN)
        .content(objectMapper.writeValueAsString(getDto()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

  }

  //Testes ativar usuario
  @Test
  @WithMockUser
  public void testAtivaOuinativaUsuarioAcessoNegado() throws Exception {
    mvc.perform(MockMvcRequestBuilders.put(URL_ATIVA_USUARIO)
        .content(objectMapper.writeValueAsString(new AtivaOuInativaUsuarioDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  public void testAtivaOuinativaUsuarioNaoAutorizado() throws Exception {
    mvc.perform(MockMvcRequestBuilders.put(URL_ATIVA_USUARIO)
        .content(objectMapper.writeValueAsString(new AtivaOuInativaUsuarioDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = {"ADMIN"})
  public void testAtivaOuinativaUsuarioErroValidacao() throws Exception {
    mvc.perform(MockMvcRequestBuilders.put(URL_ATIVA_USUARIO)
        .content(objectMapper.writeValueAsString(new AtivaOuInativaUsuarioDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(roles = {"ADMIN"})
  public void testAtivaOuinativaUsuarioVaiPassar() throws Exception {
    BDDMockito.given(usuarioService.ativaOuInativaUsuario(Mockito.anyInt(), Mockito.anyBoolean())).willReturn(true);

    mvc.perform(MockMvcRequestBuilders.put(URL_ATIVA_USUARIO)
        .content(objectMapper.writeValueAsString(getAtivaDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  //Testes ativar consultor
  @Test
  @WithMockUser
  public void testAtivaOuinativaConsultorAcessoNegado() throws Exception {
    mvc.perform(MockMvcRequestBuilders.put(URL_ATIVA_CONSULTOR)
        .content(objectMapper.writeValueAsString(new AtivaOuInativaUsuarioDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  public void testAtivaOuinativaConsultorNaoAutorizado() throws Exception {
    mvc.perform(MockMvcRequestBuilders.put(URL_ATIVA_CONSULTOR)
        .content(objectMapper.writeValueAsString(new AtivaOuInativaUsuarioDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = {"USUARIO"})
  public void testAtivaOuinativaConsultorErroValidacao() throws Exception {
    mvc.perform(MockMvcRequestBuilders.put(URL_ATIVA_CONSULTOR)
        .content(objectMapper.writeValueAsString(new AtivaOuInativaUsuarioDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(roles = {"USUARIO"})
  public void testAtivaOuinativaConsultorVaiPassar() throws Exception {
    BDDMockito.given(usuarioService.ativaOuInativaUsuario(Mockito.anyInt(), Mockito.anyBoolean())).willReturn(true);

    mvc.perform(MockMvcRequestBuilders.put(URL_ATIVA_CONSULTOR)
        .content(objectMapper.writeValueAsString(getAtivaDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  private UsuarioDTO getDto() {
    return UsuarioDTO.builder()
        .senha(SENHA)
        .nome(NOME)
        .codigo(CODIGO)
        .build();
  }

  private AtivaOuInativaUsuarioDTO getAtivaDTO() {
    return AtivaOuInativaUsuarioDTO.builder()
        .ativo(true)
        .codigoUsuario(CODIGO)
        .build();
  }
}

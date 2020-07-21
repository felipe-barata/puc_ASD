package br.com.sigo.consultoria.controllers;

import br.com.sigo.consultoria.dtos.AtribuirCategoriaEmpresaDTO;
import br.com.sigo.consultoria.dtos.CategoriaDTO;
import br.com.sigo.consultoria.dtos.EmpresaDTO;
import br.com.sigo.consultoria.exceptions.CategoriaNaoEncontradaException;
import br.com.sigo.consultoria.exceptions.EmpresaNaoEncontradaException;
import br.com.sigo.consultoria.services.EmpresaService;
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
public class TestEmpresaController {

  private static final String URL_ATUALIZAR = "/api/empresa";
  private static final String URL_CATEGORIA = "/api/empresa/atribuirCategoria";

  private static final String CNPJ = "12345678901234";
  private static final String NUMERO = "S/Número";
  private static final String BAIRRO = "Jd Califórnia";
  private static final Integer CATEGORIA = 1;
  private static final String CIDADE = "Ribeirão Preto";
  private static final Integer PAIS = 55;
  private static final String POSTAL = "14025420";
  private static final String ENDERECO = "Rua Jardim Macedo";
  private static final String ESTADO = "SP";
  private static final String NOME = "Empresa teste";

  @MockBean
  private EmpresaService empresaService;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @WithMockUser(roles = {"USUARIO"})
  public void testAtualizaEmpresaVaiRetornarErroValidacao() throws Exception {
    BDDMockito.when(empresaService.atualizar(Mockito.any(EmpresaDTO.class))).thenReturn(new EmpresaDTO());

    mvc.perform(MockMvcRequestBuilders.post(URL_ATUALIZAR)
        .content(objectMapper.writeValueAsString(EmpresaDTO.builder().build()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser()
  public void testAtualizaEmpresaVaiRetornarAcessoNegado() throws Exception {
    BDDMockito.when(empresaService.atualizar(Mockito.any(EmpresaDTO.class))).thenReturn(new EmpresaDTO());

    mvc.perform(MockMvcRequestBuilders.post(URL_ATUALIZAR)
        .content(objectMapper.writeValueAsString(EmpresaDTO.builder().build()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  public void testAtualizaEmpresaVaiRetornarNaoAutorizado() throws Exception {
    BDDMockito.when(empresaService.atualizar(Mockito.any(EmpresaDTO.class))).thenReturn(new EmpresaDTO());

    mvc.perform(MockMvcRequestBuilders.post(URL_ATUALIZAR)
        .content(objectMapper.writeValueAsString(EmpresaDTO.builder().build()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = {"USUARIO"})
  public void testAtualizaEmpresaVaiRetornarSucesso() throws Exception {
    BDDMockito.when(empresaService.atualizar(Mockito.any(EmpresaDTO.class))).thenReturn(EmpresaDTO.builder().id(1).build());

    mvc.perform(MockMvcRequestBuilders.post(URL_ATUALIZAR)
        .content(objectMapper.writeValueAsString(getDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = {"USUARIO"})
  public void testAtualizaEmpresaVaiRetornarCategoriaNaoExiste() throws Exception {
    BDDMockito.given(empresaService.atualizar(Mockito.any(EmpresaDTO.class))).willThrow(CategoriaNaoEncontradaException.class);

    mvc.perform(MockMvcRequestBuilders.post(URL_ATUALIZAR)
        .content(objectMapper.writeValueAsString(getDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testAtribuirCategoriaNaoAutorizado() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post(URL_CATEGORIA)
        .content(objectMapper.writeValueAsString(getDTOCategoria()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser
  public void testAtribuirCategoriaAcessoNegado() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post(URL_CATEGORIA)
        .content(objectMapper.writeValueAsString(getDTOCategoria()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = {"USUARIO"})
  public void testAtribuirCategoriaErroValidacao() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post(URL_CATEGORIA)
        .content(objectMapper.writeValueAsString(new CategoriaDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(roles = {"USUARIO"})
  public void testAtribuirCategoriaSucesso() throws Exception {
    BDDMockito.given(empresaService.atribuirCategoriaAEmpresa(CNPJ, CATEGORIA)).willReturn(true);

    mvc.perform(MockMvcRequestBuilders.post(URL_CATEGORIA)
        .content(objectMapper.writeValueAsString(getDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = {"USUARIO"})
  public void testAtribuirCategoriaVaiRetornarNaoEncontrouCategoria() throws Exception {
    BDDMockito.given(empresaService.atribuirCategoriaAEmpresa(CNPJ, CATEGORIA)).willThrow(CategoriaNaoEncontradaException.class);

    mvc.perform(MockMvcRequestBuilders.post(URL_CATEGORIA)
        .content(objectMapper.writeValueAsString(getDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(roles = {"USUARIO"})
  public void testAtribuirCategoriaVaiRetornarNaoEncontrouEmpresa() throws Exception {
    BDDMockito.given(empresaService.atribuirCategoriaAEmpresa(CNPJ, CATEGORIA)).willThrow(EmpresaNaoEncontradaException.class);

    mvc.perform(MockMvcRequestBuilders.post(URL_CATEGORIA)
        .content(objectMapper.writeValueAsString(getDTO()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  private AtribuirCategoriaEmpresaDTO getDTOCategoria() {
    return AtribuirCategoriaEmpresaDTO.builder()
        .categoria(CATEGORIA)
        .cnpj(CNPJ)
        .build();
  }

  private EmpresaDTO getDTO() {
    return EmpresaDTO.builder()
        .bairro(BAIRRO)
        .categoria(CATEGORIA)
        .cidade(CIDADE)
        .cnpj(CNPJ)
        .codigoPais(PAIS)
        .codigoPostal(POSTAL)
        .endereco(ENDERECO)
        .estado(ESTADO)
        .nomeEmpresa(NOME)
        .numero(NUMERO)
        .build();
  }
}

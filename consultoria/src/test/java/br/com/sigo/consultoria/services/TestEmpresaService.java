package br.com.sigo.consultoria.services;

import br.com.sigo.consultoria.domain.Categoria;
import br.com.sigo.consultoria.domain.Empresa;
import br.com.sigo.consultoria.dtos.EmpresaDTO;
import br.com.sigo.consultoria.exceptions.ConsultoriaException;
import br.com.sigo.consultoria.repository.CategoriaRepository;
import br.com.sigo.consultoria.repository.EmpresaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestEmpresaService {

  private static final String CNPJ = "1234";
  private static final String NUMERO = "S/Número";
  private static final String BAIRRO = "Jd Califórnia";
  private static final Integer CATEGORIA = 1;
  private static final String CIDADE = "Ribeirão Preto";
  private static final Integer PAIS = 55;
  private static final String POSTAL = "14025420";
  private static final String ENDERECO = "Rua Jardim Macedo";
  private static final String ESTADO = "SP";
  private static final String NOME = "Empresa teste";
  private static final Integer NOVA_CATEGORIA = 2;
  @MockBean
  private EmpresaRepository empresaRepository;

  @MockBean
  private CategoriaRepository categoriaRepository;

  @Autowired
  private EmpresaService empresaService;

  @Test
  public void testEncontraEmpresa() throws ConsultoriaException {
    BDDMockito.when(empresaRepository.findByCnpj(CNPJ)).thenReturn(Optional.of(Empresa.builder().cnpj(CNPJ).build()));
    Assertions.assertTrue(empresaService.encontraEmpresa(CNPJ).isPresent());
  }

  @Test
  public void testNaoEncontraEmpresa() throws ConsultoriaException {
    BDDMockito.when(empresaRepository.findByCnpj(CNPJ)).thenReturn(Optional.empty());
    Assertions.assertTrue(empresaService.encontraEmpresa(CNPJ).isEmpty());
  }

  @Test
  public void testAtualizar() throws ConsultoriaException {
    BDDMockito.when(categoriaRepository.findById(CATEGORIA)).thenReturn(Optional.of(Categoria.builder().id(CATEGORIA).build()));
    BDDMockito.when(empresaRepository.findByCnpj(CNPJ)).thenReturn(Optional.of(Empresa.builder().cnpj(CNPJ).build()));
    BDDMockito.when(empresaRepository.save(Mockito.any(Empresa.class))).thenReturn(Empresa.builder().build());
    Assertions.assertNotNull(empresaService.atualizar(getEmpresaDto()));

  }

  @Test
  public void testInserir() throws ConsultoriaException {
    BDDMockito.when(categoriaRepository.findById(CATEGORIA)).thenReturn(Optional.of(Categoria.builder().id(CATEGORIA).build()));
    BDDMockito.when(empresaRepository.findByCnpj(CNPJ)).thenReturn(Optional.empty());
    BDDMockito.when(empresaRepository.save(Mockito.any(Empresa.class))).thenReturn(Empresa.builder().build());
    Assertions.assertNotNull(empresaService.atualizar(getEmpresaDto()));
  }

  @Test
  public void testAtribuirCategoriaAEmpresa() throws ConsultoriaException {
    BDDMockito.when(categoriaRepository.findById(NOVA_CATEGORIA)).thenReturn(Optional.of(Categoria.builder().id(NOVA_CATEGORIA).build()));
    BDDMockito.when(empresaRepository.findByCnpj(CNPJ)).thenReturn(Optional.of(Empresa.builder().cnpj(CNPJ).build()));
    BDDMockito.when(empresaRepository.atualizaCodigoCategoriaEmpresa(NOVA_CATEGORIA, CNPJ)).thenReturn(1);
    Assertions.assertTrue(empresaService.atribuirCategoriaAEmpresa(CNPJ, NOVA_CATEGORIA));
  }

  private EmpresaDTO getEmpresaDto() {
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

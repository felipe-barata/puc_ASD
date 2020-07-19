package br.com.sigo.consultoria.repository;

import br.com.sigo.consultoria.domain.Categoria;
import br.com.sigo.consultoria.domain.Empresa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestEmpresaRepository {

  private static final String NUMERO = "S/Número";
  private static final String NOME_EMPRESA = "Empresa Fictícia";
  private static final String ESTADO = "SP";
  private static final String ENDERECO = "Avenida Presidente Vargas";
  private static final String CODIGO_POSTAL = "14024-420";
  private static final Integer CODIGO_PAIS = 55;
  private static final String CNPJ = "3212345670910939";
  private static final String CIDADE = "Ribeirão Preto";
  private static final String BAIRRO = "JD Sumaré";
  private static final String DESC_CATEGORIA = "Informática";

  @Autowired
  private EmpresaRepository empresaRepository;

  @Autowired
  private CategoriaRepository categoriaRepository;

  @BeforeAll
  public void setUp() {
    Categoria save = categoriaRepository.save(getCategoria());
    Categoria save2 = categoriaRepository.save(getCategoria());
    this.empresaRepository.save(getEmpresa(save));
  }

  @Test
  public void testFindByCnpj() {
    Optional<Empresa> byCnpj = empresaRepository.findByCnpj(CNPJ);
    Assertions.assertTrue(byCnpj.isPresent());
  }

  @Test
  public void testAtualizaCodigoCategoriaEmpresa() {
    List<Categoria> save = categoriaRepository.findAll();
    Assertions.assertNotNull(save);

    Categoria cat2 = save.get(save.size() - 1);

    Boolean aBoolean = empresaRepository.atualizaCodigoCategoriaEmpresa(cat2.getId(), CNPJ) > 0;
    Assertions.assertTrue(aBoolean);
    Optional<Empresa> byId = empresaRepository.findByCnpj(CNPJ);
    Assertions.assertTrue(byId.isPresent() && byId.get().getCategoria().getId().equals(cat2.getId()));
  }

  @AfterAll
  public final void tearDown() {
    this.empresaRepository.deleteAll();
    categoriaRepository.deleteAll();
  }

  private Empresa getEmpresa(Categoria categoria) {
    return Empresa.builder()
        .numero(NUMERO)
        .nomeEmpresa(NOME_EMPRESA)
        .estado(ESTADO)
        .endereco(ENDERECO)
        .codigoPostal(CODIGO_POSTAL)
        .codigoPais(CODIGO_PAIS)
        .cnpj(CNPJ)
        .cidade(CIDADE)
        .bairro(BAIRRO)
        .categoria(categoria)
        .build();
  }

  private Categoria getCategoria() {
    return Categoria.builder()
        .descricao(DESC_CATEGORIA)
        .build();
  }

}

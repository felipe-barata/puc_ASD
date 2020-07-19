package br.com.sigo.consultoria.services.impl;

import br.com.sigo.consultoria.domain.Empresa;
import br.com.sigo.consultoria.dtos.EmpresaDTO;
import br.com.sigo.consultoria.exceptions.CategoriaNaoEncontradaException;
import br.com.sigo.consultoria.exceptions.ConsultoriaException;
import br.com.sigo.consultoria.exceptions.EmpresaNaoEncontradaException;
import br.com.sigo.consultoria.repository.CategoriaRepository;
import br.com.sigo.consultoria.repository.EmpresaRepository;
import br.com.sigo.consultoria.services.EmpresaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class EmpresaServiceImpl implements EmpresaService {

  @Autowired
  private EmpresaRepository empresaRepository;

  @Autowired
  private CategoriaRepository categoriaRepository;

  @Override
  public Optional<Empresa> encontraEmpresa(String cnpj) throws ConsultoriaException {
    try {
      log.info("encontraEmpresa");
      return empresaRepository.findByCnpj(cnpj);
    } catch (Exception e) {
      log.error("encontraEmpresa - Erro: ", e);
      throw new ConsultoriaException(e.getMessage());
    }
  }

  @Override
  public EmpresaDTO atualizar(EmpresaDTO empresa) throws ConsultoriaException {
    try {
      log.info("atualizar");
      Optional<Empresa> optEmpresa = empresaRepository.findByCnpj(empresa.getCnpj());
      Empresa e = Empresa.builder()
          .bairro(empresa.getBairro())
          .cidade(empresa.getCidade())
          .cnpj(empresa.getCnpj())
          .codigoPais(empresa.getCodigoPais())
          .codigoPostal(empresa.getCodigoPostal())
          .endereco(empresa.getEndereco())
          .estado(empresa.getEstado())
          .nomeEmpresa(empresa.getNomeEmpresa())
          .numero(empresa.getNumero())
          .build();
      if (optEmpresa.isPresent()) {
        log.debug("atualizar - existe empresa com esse CNPJ");
        e.setId(optEmpresa.get().getId());
      } else {
        log.debug("atualizar - nao existe empresa com esse CNPJ");
      }
      e = empresaRepository.save(e);
      if (e != null) {
        empresa.setId(e.getId());
      }
      return empresa;
    } catch (Exception e) {
      log.error("atualizar - Erro: ", e);
      throw new ConsultoriaException(e.getMessage());
    }
  }

  @Override
  public boolean atribuirCategoriaAEmpresa(String cnpj, Integer categoria) throws ConsultoriaException {
    try {
      if (categoriaRepository.findById(categoria).isEmpty()) {
        log.warn("atribuirCategoriaAEmpresa - categoria: {} nao encontrada", categoria);
        throw new CategoriaNaoEncontradaException(categoria);
      }
      log.info("atribuirCategoriaAEmpresa - categoria: {}", categoria);

      if (empresaRepository.findByCnpj(cnpj).isEmpty()) {
        log.warn("atribuirCategoriaAEmpresa - empresa nao encontrada para o cnpj");
        throw new EmpresaNaoEncontradaException(cnpj);
      }

      return empresaRepository.atualizaCodigoCategoriaEmpresa(categoria, cnpj);

    } catch (Exception e) {
      log.error("atribuirCategoriaAEmpresa - Erro: ", e);
      throw new ConsultoriaException(e.getMessage());
    }
  }

}

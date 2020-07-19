package br.com.sigo.consultoria.services;

import br.com.sigo.consultoria.domain.Empresa;
import br.com.sigo.consultoria.dtos.EmpresaDTO;
import br.com.sigo.consultoria.exceptions.ConsultoriaException;

import java.util.Optional;

public interface EmpresaService {

  Optional<Empresa> encontraEmpresa(String cnpj) throws ConsultoriaException;

  EmpresaDTO atualizar(EmpresaDTO empresa) throws ConsultoriaException;

  boolean atribuirCategoriaAEmpresa(String cnpj, Integer categoria) throws ConsultoriaException;
}

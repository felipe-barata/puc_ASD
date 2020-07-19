package br.com.sigo.consultoria.services;

import br.com.sigo.consultoria.domain.UsuarioEmpresa;
import br.com.sigo.consultoria.exceptions.ConsultoriaException;
import br.com.sigo.consultoria.response.Response;

import java.util.List;

public interface UsuarioEmpresaService {

  Response<List<UsuarioEmpresa>> atualizarUsuarioEmpresa(List<UsuarioEmpresa> list) throws ConsultoriaException;
}

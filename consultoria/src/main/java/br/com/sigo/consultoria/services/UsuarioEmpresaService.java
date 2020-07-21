package br.com.sigo.consultoria.services;

import br.com.sigo.consultoria.dtos.UsuarioEmpresaDTO;
import br.com.sigo.consultoria.exceptions.ConsultoriaException;
import br.com.sigo.consultoria.response.Response;

import java.util.List;

public interface UsuarioEmpresaService {

  Response<List<UsuarioEmpresaDTO>> atualizarUsuarioEmpresa(List<UsuarioEmpresaDTO> list) throws ConsultoriaException;
}

package br.com.sigo.consultoria.services;

import br.com.sigo.consultoria.domain.Usuario;
import br.com.sigo.consultoria.dtos.UsuarioDTO;
import br.com.sigo.consultoria.enums.PerfilEnum;
import br.com.sigo.consultoria.exceptions.ConsultoriaException;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

  Optional<Usuario> buscarUsername(String codigo);

  UsuarioDTO atualizar(UsuarioDTO usuario, List<PerfilEnum> perfis) throws ConsultoriaException;

  boolean ativaOuInativaUsuario(Integer usuario, Boolean desativar) throws ConsultoriaException;
}

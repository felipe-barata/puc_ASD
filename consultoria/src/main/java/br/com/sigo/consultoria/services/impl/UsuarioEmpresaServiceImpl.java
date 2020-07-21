package br.com.sigo.consultoria.services.impl;

import br.com.sigo.consultoria.domain.UsuarioEmpresa;
import br.com.sigo.consultoria.domain.chave.UsuarioEmpresaPK;
import br.com.sigo.consultoria.dtos.UsuarioEmpresaDTO;
import br.com.sigo.consultoria.exceptions.ConsultoriaException;
import br.com.sigo.consultoria.repository.EmpresaRepository;
import br.com.sigo.consultoria.repository.UsuarioEmpresaRepository;
import br.com.sigo.consultoria.repository.UsuarioRepository;
import br.com.sigo.consultoria.response.Response;
import br.com.sigo.consultoria.services.UsuarioEmpresaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UsuarioEmpresaServiceImpl implements UsuarioEmpresaService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private EmpresaRepository empresaRepository;

  @Autowired
  private UsuarioEmpresaRepository usuarioEmpresaRepository;

  @Override
  public Response<List<UsuarioEmpresaDTO>> atualizarUsuarioEmpresa(List<UsuarioEmpresaDTO> list) throws ConsultoriaException {
    Response<List<UsuarioEmpresaDTO>> response = new Response<>();
    response.setErrors(new ArrayList<>());
    response.setData(new ArrayList<>());
    log.info("atualizarUsuarioEmpresa");
    try {
      for (UsuarioEmpresaDTO user : list) {
        boolean inserir = true;
        if (usuarioRepository.findById(user.getIdUsuario()).isEmpty()) {
          String message = MessageFormat.format("usuario com ID: {0} nao encontrado", user.getIdUsuario());
          response.getErrors().add(message);
          log.warn("atualizarUsuarioEmpresa - {}", message);
          inserir = false;
        }

        if (empresaRepository.findById(user.getIdEmpresa()).isEmpty()) {
          String message = MessageFormat.format("empresa com ID: {0} nao encontrada", user.getIdEmpresa());
          response.getErrors().add(message);
          log.warn("atualizarUsuarioEmpresa - {}", message);
          inserir = false;
        }

        if (inserir) {
          log.debug("atualizarUsuarioEmpresa - inserir usuario: {} e empresa: {}", user.getIdUsuario(), user.getIdEmpresa());
          usuarioEmpresaRepository.save(UsuarioEmpresa.builder().
              chave(UsuarioEmpresaPK.builder().
                  empresaId(user.getIdEmpresa()).usuarioId(user.getIdUsuario()).build()).build());
          response.getData().add(user);
        }
      }
    } catch (Exception e) {
      log.error("atualizarUsuarioEmpresa - erro: ", e);
      throw new ConsultoriaException(e.getMessage());
    }

    return response;
  }
}

package br.com.sigo.consultoria.services.impl;

import br.com.sigo.consultoria.domain.Perfis;
import br.com.sigo.consultoria.domain.Usuario;
import br.com.sigo.consultoria.dtos.UsuarioDTO;
import br.com.sigo.consultoria.enums.PerfilEnum;
import br.com.sigo.consultoria.exceptions.ConsultoriaException;
import br.com.sigo.consultoria.exceptions.ErroAtualizarUsuarioException;
import br.com.sigo.consultoria.exceptions.UsuarioNaoEncontradoException;
import br.com.sigo.consultoria.repository.PerfisRepository;
import br.com.sigo.consultoria.repository.UsuarioRepository;
import br.com.sigo.consultoria.services.UsuarioService;
import br.com.sigo.consultoria.utils.cripto.BCryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private PerfisRepository perfisRepository;

  @Autowired
  private BCryptUtils bCryptUtils;

  @Override
  public Optional<Usuario> buscarUsername(String codigo) {
    log.info("buscarUsername - codigo: {}", codigo);
    return usuarioRepository.findByCodigo(Integer.parseInt(codigo));
  }

  @Override
  public UsuarioDTO atualizar(UsuarioDTO usuario, List<PerfilEnum> perfis) throws ConsultoriaException {
    log.info("atualizar - codigo: {}", usuario.getCodigo());
    Usuario u = Usuario
        .builder()
        .codigo(usuario.getCodigo())
        .nome(usuario.getNome())
        .ativo(true)
        .senha(bCryptUtils.gerarBCrypt(usuario.getSenha())).build();

    Optional<Usuario> byCodigo = usuarioRepository.findByCodigo(usuario.getCodigo());
    if (byCodigo.isPresent()) {
      log.debug("atualizar - usuario: {} já existe", u.getCodigo());
      Usuario u1 = byCodigo.get();
      u.setId(u1.getId());
    }
    u = usuarioRepository.save(u);
    if (u == null) {
      log.warn("atualizar - erro salvar usuario na base de dados");
      throw new ErroAtualizarUsuarioException();
    }
    usuario.setId(u.getId());
    perfisRepository.apagaPerfilUsuario(u.getId());
    Usuario finalU = u;
    List<Perfis> collect = perfis.stream().map(m -> Perfis.builder().perfil(m).usuario(finalU).build()).collect(Collectors.toList());
    perfisRepository.saveAll(collect);
    return usuario;
  }

  @Override
  public boolean ativaOuInativaUsuario(Integer usuario, Boolean desativar) throws ConsultoriaException {
    log.info("ativaOuInativaUsuario - usuario: {}, desativar: {}", usuario, desativar);
    if (usuarioRepository.findByCodigo(usuario).isEmpty()) {
      log.warn("ativaOuInativaUsuario - nao encontrou usuario: {}", usuario);
      throw new UsuarioNaoEncontradoException(usuario);
    }
    boolean inativaUsuario = usuarioRepository.ativaInativaUsuario(usuario, !desativar) > 0;
    log.debug("ativaOuInativaUsuario - retorno: {}", inativaUsuario);
    return inativaUsuario;
  }
}

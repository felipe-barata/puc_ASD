package br.com.sigo.consultoria.controllers;

import br.com.sigo.consultoria.dtos.AtivaOuInativaUsuarioDTO;
import br.com.sigo.consultoria.dtos.UsuarioDTO;
import br.com.sigo.consultoria.enums.PerfilEnum;
import br.com.sigo.consultoria.exceptions.ErroAtualizarUsuarioException;
import br.com.sigo.consultoria.exceptions.ErroCodigoUsuarioOutroPerfilException;
import br.com.sigo.consultoria.exceptions.UsuarioNaoEncontradoException;
import br.com.sigo.consultoria.response.Response;
import br.com.sigo.consultoria.services.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/usuario")
@CrossOrigin(origins = "*")
@Slf4j
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  @Secured("ROLE_USUARIO")
  @PostMapping
  public ResponseEntity<Response<UsuarioDTO>> atualizaUsuarioSistema(@RequestBody @Valid UsuarioDTO dto, BindingResult bindingResult) {
    return executaAtualizacaoUsuario(dto, Arrays.asList(PerfilEnum.ROLE_USUARIO), bindingResult);
  }

  @Secured("ROLE_USUARIO")
  @PostMapping(value = "consultor")
  public ResponseEntity<Response<UsuarioDTO>> atualizaConsultor(@RequestBody @Valid UsuarioDTO dto, BindingResult bindingResult) {
    return executaAtualizacaoUsuario(dto, Arrays.asList(PerfilEnum.ROLE_CONSULTOR), bindingResult);
  }

  @Secured("ROLE_ADMIN")
  @PostMapping(value = "admin")
  public ResponseEntity<Response<UsuarioDTO>> atualizaAdmin(@RequestBody @Valid UsuarioDTO dto, BindingResult bindingResult) {
    return executaAtualizacaoUsuario(dto, Arrays.asList(PerfilEnum.ROLE_ADMIN, PerfilEnum.ROLE_USUARIO), bindingResult);
  }

  @Secured("ROLE_ADMIN")
  @PutMapping(value = "admin/ativaOuInativa")
  public ResponseEntity ativaOuinativaUsuario(@RequestBody @Valid AtivaOuInativaUsuarioDTO dto, BindingResult bindingResult) {
    Response<UsuarioDTO> response = new Response<>();
    if (bindingResult != null && bindingResult.hasErrors()) {
      log.warn("ativaOuInativa - erros de validacao");
      response.setErrors(new ArrayList<>());
      bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
      return ResponseEntity.badRequest().body(response);
    }
    try {
      boolean retorno = usuarioService.ativaOuInativaUsuario(dto.getCodigoUsuario(), dto.getAtivo());
      log.debug("ativaOuInativa - retorno: {}", retorno);
      if (retorno) {
        return ResponseEntity.ok().build();
      }
    } catch (UsuarioNaoEncontradoException e) {
      response.setErrors(new ArrayList<>());
      response.getErrors().add(e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    } catch (Exception e) {
      response.setErrors(new ArrayList<>());
      response.getErrors().add(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    return ResponseEntity.noContent().build();
  }

  @Secured("ROLE_USUARIO")
  @PutMapping(value = "ativaOuInativa")
  public ResponseEntity ativaOuinativaConsultor(@RequestBody @Valid AtivaOuInativaUsuarioDTO dto, BindingResult bindingResult) {
    Response<UsuarioDTO> response = new Response<>();
    if (bindingResult != null && bindingResult.hasErrors()) {
      log.warn("ativaOuinativaConsultor - erros de validacao");
      response.setErrors(new ArrayList<>());
      bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
      return ResponseEntity.badRequest().body(response);
    }
    try {
      boolean retorno = usuarioService.ativaOuInativaUsuario(dto.getCodigoUsuario(), dto.getAtivo());
      log.debug("ativaOuinativaConsultor - retorno: {}", retorno);
      if (retorno) {
        return ResponseEntity.ok().build();
      }
    } catch (UsuarioNaoEncontradoException e) {
      response.setErrors(new ArrayList<>());
      response.getErrors().add(e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    } catch (Exception e) {
      response.setErrors(new ArrayList<>());
      response.getErrors().add(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    return ResponseEntity.noContent().build();
  }

  private ResponseEntity<Response<UsuarioDTO>> executaAtualizacaoUsuario(UsuarioDTO dto, List<PerfilEnum> perfil, BindingResult bindingResult) {
    log.info("executaAtualizacaoUsuario - codigo: {}, perfil: {}", dto.getCodigo(), perfil);
    Response<UsuarioDTO> response = new Response<>();
    if (bindingResult != null && bindingResult.hasErrors()) {
      log.warn("executaAtualizacaoUsuario - erros de validacao");
      response.setErrors(new ArrayList<>());
      bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
      return ResponseEntity.badRequest().body(response);
    }
    try {
      UsuarioDTO usuarioDTO = usuarioService.atualizar(dto, perfil);
      response.setData(usuarioDTO);
    } catch (ErroAtualizarUsuarioException e) {
      log.error("executaAtualizacaoUsuario - erro atualizar usuario: ", e);
      response.setErrors(new ArrayList<>());
      response.getErrors().add(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    } catch (ErroCodigoUsuarioOutroPerfilException e) {
      log.error("executaAtualizacaoUsuario - erro codigo ja existe para outro perfil: ", e);
      response.setErrors(new ArrayList<>());
      response.getErrors().add(e.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    } catch (Exception e) {
      log.error("executaAtualizacaoUsuario - erro sistema: ", e);
      response.setErrors(new ArrayList<>());
      response.getErrors().add(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    return ResponseEntity.ok(response);
  }
}

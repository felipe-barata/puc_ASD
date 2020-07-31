package br.com.sigo.consultoria.controllers;

import br.com.sigo.consultoria.dtos.AtivaOuInativaUsuarioDTO;
import br.com.sigo.consultoria.dtos.ConsultarUsuarioDTO;
import br.com.sigo.consultoria.dtos.UsuarioDTO;
import br.com.sigo.consultoria.enums.PerfilEnum;
import br.com.sigo.consultoria.exceptions.ErroAtualizarUsuarioException;
import br.com.sigo.consultoria.exceptions.ErroCodigoUsuarioOutroPerfilException;
import br.com.sigo.consultoria.exceptions.UsuarioNaoEncontradoException;
import br.com.sigo.consultoria.response.Response;
import br.com.sigo.consultoria.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

  @Operation(summary = "Inserir ou atualizar um usuário no sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuário inserido/atualizado"),
      @ApiResponse(responseCode = "409", description = "O código informado existe para outro usuário", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @Secured("ROLE_USUARIO")
  @PostMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Response<UsuarioDTO>> atualizaUsuarioSistema(@RequestBody @Valid UsuarioDTO dto, BindingResult bindingResult) {
    return executaAtualizacaoUsuario(dto, Arrays.asList(PerfilEnum.ROLE_USUARIO), bindingResult);
  }

  @Operation(summary = "Inserir ou atualizar um consultor")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Consultor inserido/atualizado"),
      @ApiResponse(responseCode = "409", description = "O código informado existe para outro usuário", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @Secured("ROLE_USUARIO")
  @PostMapping(value = "consultor", produces = "application/json", consumes = "application/json")
  public ResponseEntity<Response<UsuarioDTO>> atualizaConsultor(@RequestBody @Valid UsuarioDTO dto, BindingResult bindingResult) {
    return executaAtualizacaoUsuario(dto, Arrays.asList(PerfilEnum.ROLE_CONSULTOR), bindingResult);
  }

  @Operation(summary = "Inserir ou atualizar um administrador no sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Administrador inserido/atualizado"),
      @ApiResponse(responseCode = "409", description = "O código informado existe para outro usuário", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @Secured("ROLE_ADMIN")
  @PostMapping(value = "admin", produces = "application/json", consumes = "application/json")
  public ResponseEntity<Response<UsuarioDTO>> atualizaAdmin(@RequestBody @Valid UsuarioDTO dto, BindingResult bindingResult) {
    return executaAtualizacaoUsuario(dto, Arrays.asList(PerfilEnum.ROLE_ADMIN, PerfilEnum.ROLE_USUARIO, PerfilEnum.ROLE_CONSULTOR), bindingResult);
  }

  @Operation(summary = "Ativar/Inativar um usuário")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuário ativado/inativado"),
      @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @Secured("ROLE_ADMIN")
  @PutMapping(value = "admin/ativaOuInativa", produces = "application/json", consumes = "application/json")
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

  @Operation(summary = "Ativar/Inativar um consultor")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Consultor ativado/inativado"),
      @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @Secured("ROLE_USUARIO")
  @PutMapping(value = "ativaOuInativa", produces = "application/json", consumes = "application/json")
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

  @Operation(summary = "Listar usuários cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Retornou usuários"),
      @ApiResponse(responseCode = "204", description = "Nenhum usuário encontrado", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @Secured("ROLE_USUARIO")
  @GetMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Response<Page<UsuarioDTO>>> listarUsuarios(@RequestBody @Valid ConsultarUsuarioDTO dto, BindingResult bindingResult) {
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Listar consultores cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Retornou consultores"),
      @ApiResponse(responseCode = "204", description = "Nenhum consultor encontrado", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @Secured("ROLE_USUARIO")
  @GetMapping(value = "consultor", produces = "application/json", consumes = "application/json")
  public ResponseEntity<Response<Page<UsuarioDTO>>> listarConsultor(@RequestBody @Valid ConsultarUsuarioDTO dto, BindingResult bindingResult) {
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Listar administradores cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Retornou administradores"),
      @ApiResponse(responseCode = "204", description = "Nenhum administrador encontrado", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @Secured("ROLE_ADMIN")
  @GetMapping(value = "admin", produces = "application/json", consumes = "application/json")
  public ResponseEntity<Response<Page<UsuarioDTO>>> listarAdmin(@RequestBody @Valid ConsultarUsuarioDTO dto, BindingResult bindingResult) {
    return ResponseEntity.ok().build();
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

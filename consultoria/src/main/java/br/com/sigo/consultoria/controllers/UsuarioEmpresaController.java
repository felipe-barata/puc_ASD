package br.com.sigo.consultoria.controllers;

import br.com.sigo.consultoria.dtos.UsuarioDTO;
import br.com.sigo.consultoria.dtos.UsuarioEmpresaDTO;
import br.com.sigo.consultoria.response.Response;
import br.com.sigo.consultoria.services.UsuarioEmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/usuarioEmpresa")
@CrossOrigin(origins = "*")
@Slf4j
public class UsuarioEmpresaController {

  @Autowired
  private UsuarioEmpresaService usuarioEmpresaService;

  @Operation(summary = "Atribuir usuarios a empresas")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso"),
      @ApiResponse(responseCode = "206", description = "Alguns registros não foram processados"),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @Secured("ROLE_USUARIO")
  @PostMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Response> atribuiUsuarioEmpresa(@RequestBody @Valid List<@Valid UsuarioEmpresaDTO> lista,
                                                        BindingResult bindingResult) {
    Response<UsuarioDTO> response = new Response<>();
    if (bindingResult != null && bindingResult.hasErrors()) {
      log.warn("atribuiUsuarioEmpresa - erros de validacao");
      response.setErrors(new ArrayList<>());
      bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
      return ResponseEntity.badRequest().body(response);
    }

    try {
      if (lista == null || lista.isEmpty()) {
        log.warn("atribuiUsuarioEmpresa - lista vazia");
        response.setErrors(new ArrayList<>());
        response.getErrors().add("A lista informada esta vazia");
        return ResponseEntity.badRequest().body(response);
      }

      Response<List<UsuarioEmpresaDTO>> listResponse = usuarioEmpresaService.atualizarUsuarioEmpresa(lista);
      if (listResponse.getErrors() == null || listResponse.getErrors().isEmpty()) {
        return ResponseEntity.ok(listResponse);
      } else {
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(listResponse);
      }
    } catch (Exception e) {
      response.setErrors(new ArrayList<>());
      response.getErrors().add(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

}

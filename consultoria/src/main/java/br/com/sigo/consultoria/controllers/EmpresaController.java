package br.com.sigo.consultoria.controllers;

import br.com.sigo.consultoria.dtos.AtribuirCategoriaEmpresaDTO;
import br.com.sigo.consultoria.dtos.ConsultarEmpresaDTO;
import br.com.sigo.consultoria.dtos.EmpresaDTO;
import br.com.sigo.consultoria.exceptions.CategoriaNaoEncontradaException;
import br.com.sigo.consultoria.exceptions.EmpresaNaoEncontradaException;
import br.com.sigo.consultoria.response.Response;
import br.com.sigo.consultoria.services.EmpresaService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/empresa")
@CrossOrigin(origins = "*")
@Slf4j
public class EmpresaController {

  @Autowired
  private EmpresaService empresaService;

  @Operation(summary = "Insere/Atualiza uma Empresa")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Empresa criada/atualizada"),
      @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @PostMapping(produces = "application/json", consumes = "application/json")
  @Secured("ROLE_USUARIO")
  public ResponseEntity<Response<EmpresaDTO>> atualizaEmpresa(@RequestBody @Valid EmpresaDTO dto, BindingResult bindingResult) {
    Response<EmpresaDTO> response = new Response<>();
    if (bindingResult != null && bindingResult.hasErrors()) {
      log.warn("ativaOuInativa - erros de validacao");
      response.setErrors(new ArrayList<>());
      bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
      return ResponseEntity.badRequest().body(response);
    }
    try {
      EmpresaDTO empresa = empresaService.atualizar(dto);
      if (empresa != null && empresa.getId() != null) {
        log.debug("atualizaEmpresa - empresa atualizada/inserida");
        response.setData(empresa);
        return ResponseEntity.ok(response);
      }
    } catch (CategoriaNaoEncontradaException e) {
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

  @Operation(summary = "Atribui uma categoria para uma Empresa")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Categoria da empresa atualizada"),
      @ApiResponse(responseCode = "404", description = "Categoria ou Empresa não encontrada", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @PostMapping(value = "atribuirCategoria", produces = "application/json", consumes = "application/json")
  @Secured("ROLE_USUARIO")
  public ResponseEntity atribuirCategoria(@RequestBody @Valid AtribuirCategoriaEmpresaDTO dto, BindingResult bindingResult) {
    Response<EmpresaDTO> response = new Response<>();
    if (bindingResult != null && bindingResult.hasErrors()) {
      log.warn("atribuirCategoria - erros de validacao");
      response.setErrors(new ArrayList<>());
      bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
      return ResponseEntity.badRequest().body(response);
    }
    try {
      boolean retorno = empresaService.atribuirCategoriaAEmpresa(dto.getCnpj(), dto.getCategoria());
      if (retorno) {
        log.debug("atribuirCategoria - sucesso");
        return ResponseEntity.ok().build();
      }
    } catch (CategoriaNaoEncontradaException | EmpresaNaoEncontradaException e) {
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

  @Operation(summary = "Consultar empresa com base em filtros")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Retornou empresa(s)"),
      @ApiResponse(responseCode = "204", description = "Nenhuma empresa encontrada para o filtro", content = @Content()),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @GetMapping(produces = "application/json", consumes = "application/json")
  @Secured("ROLE_USUARIO")
  public ResponseEntity<Response<List<EmpresaDTO>>> consultarEmpresa(@Valid @RequestBody ConsultarEmpresaDTO dto) {
    return ResponseEntity.ok().build();
  }

}

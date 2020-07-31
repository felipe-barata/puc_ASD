package br.com.sigo.consultoria.controllers;

import br.com.sigo.consultoria.dtos.ConsultaContratoIntegracaoDTO;
import br.com.sigo.consultoria.dtos.ContratoIntegracaoDTO;
import br.com.sigo.consultoria.dtos.IntegracaoDTO;
import br.com.sigo.consultoria.dtos.sql.SQLResponseDTO;
import br.com.sigo.consultoria.dtos.sql.SQLResponseListDTO;
import br.com.sigo.consultoria.dtos.sql.SQLStatementDTO;
import br.com.sigo.consultoria.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/integracoes")
@CrossOrigin(origins = "*")
@Slf4j
public class IntegracoesController {


  @Operation(summary = "Atribuir contrato a integracoes")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso"),
      @ApiResponse(responseCode = "206", description = "Alguns registros não foram processados"),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @Secured("ROLE_USUARIO")
  @PostMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Response<List<ContratoIntegracaoDTO>>> atualizarContratoIntegracao(@RequestBody List<ContratoIntegracaoDTO> list) {
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Consultar integrações")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso"),
      @ApiResponse(responseCode = "204", description = "Alguns registros não foram processados", content = @Content()),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @Secured("ROLE_USUARIO")
  @GetMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Response<Page<IntegracaoDTO>>> consultaIntegracao(@RequestBody ConsultaContratoIntegracaoDTO dto) {
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Realiza a consulta de uma integração no módulo de gestão, retornando um resultado")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso"),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @Secured("ROLE_USUARIO")
  @GetMapping(produces = "application/json", consumes = "application/json", value = "consulta")
  public ResponseEntity<Response<SQLResponseDTO>> consultarModuloGestao(@RequestBody SQLStatementDTO sql) {
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Realiza a consulta de uma integração no módulo de gestão, retornando uma lista de resultados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso"),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @Secured("ROLE_USUARIO")
  @GetMapping(produces = "application/json", consumes = "application/json", value = "consultaLista")
  public ResponseEntity<Response<SQLResponseListDTO>> consultarModuloGestaoLista(@RequestBody SQLStatementDTO sql) {
    return ResponseEntity.ok().build();
  }
}

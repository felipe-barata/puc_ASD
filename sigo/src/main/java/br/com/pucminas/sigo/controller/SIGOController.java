package br.com.pucminas.sigo.controller;

import br.com.pucminas.sigo.dto.SQLResponseDTO;
import br.com.pucminas.sigo.dto.SQLResponseListDTO;
import br.com.pucminas.sigo.dto.SQLStatementDTO;
import br.com.pucminas.sigo.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/sigo")
@CrossOrigin(origins = "*")
@Slf4j
public class SIGOController {

  @Operation(summary = "Realiza uma consulta na base de dados do sistema informado, retornando um resultado")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso"),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @GetMapping(produces = "application/json", consumes = "application/json", value = "consulta")
  public ResponseEntity<Response<SQLResponseDTO>> consultarModuloGestao(@RequestBody SQLStatementDTO sql) {
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Realiza uma consulta na base de dados do sistema informado, retornando uma lista de resultados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso"),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @GetMapping(produces = "application/json", consumes = "application/json", value = "consultaLista")
  public ResponseEntity<Response<SQLResponseListDTO>> consultarModuloGestaoLista(@RequestBody SQLStatementDTO sql) {
    return ResponseEntity.ok().build();
  }

}

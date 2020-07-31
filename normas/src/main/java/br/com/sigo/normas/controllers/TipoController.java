package br.com.sigo.normas.controllers;

import br.com.sigo.normas.dtos.TipoDTO;
import br.com.sigo.normas.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/tipo")
@CrossOrigin(origins = "*")
@Slf4j
public class TipoController {

  @Operation(summary = "Inserir/Atualizar tipo")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso"),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @PostMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Response<TipoDTO>> atualizarCategoria(@Valid @RequestBody TipoDTO dto) {
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Listar tipos")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso"),
      @ApiResponse(responseCode = "204", description = "Nenhuma categoria", content = @Content()),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @GetMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Response<Page<TipoDTO>>> listarCategoria() {
    return ResponseEntity.ok().build();
  }

}

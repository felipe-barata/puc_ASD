package br.com.sigo.consultoria.controllers;

import br.com.sigo.consultoria.dtos.ContratoDTO;
import br.com.sigo.consultoria.dtos.DownloadContratoDTO;
import br.com.sigo.consultoria.dtos.RetornoContratoDTO;
import br.com.sigo.consultoria.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("api/contratos")
@CrossOrigin(origins = "*")
@Slf4j
public class ContratosController {

  @Operation(summary = "Insere/Atualiza um Contrato")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Contrato criado/atualizado"),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @PostMapping(produces = "application/json", consumes = "application/json")
  @Secured("ROLE_CONSULTOR")
  public ResponseEntity<Response<RetornoContratoDTO>> inserirContrato(@Valid @RequestBody ContratoDTO dto, BindingResult bindingResult) {
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Atualiza a quantidade de horas gastas no contrato")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Contrato criado/atualizado"),
      @ApiResponse(responseCode = "404", description = "Não encontrou o contrato", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @PutMapping(produces = "application/json", consumes = "application/json")
  @Secured("ROLE_USUARIO")
  public ResponseEntity<Response<RetornoContratoDTO>> atualizarQuantidadeHoras(@RequestParam int horasContabilizadas, @RequestParam int idContrato) {
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Lista os contratos de uma empresa")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Retornou os contratos"),
      @ApiResponse(responseCode = "204", description = "Não existem contratos para retornar", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "404", description = "Não encontrou a empresa", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @GetMapping(value = "/listar", produces = "application/json", consumes = "application/json")
  @Secured("ROLE_CONSULTOR")
  public ResponseEntity<Response<Page<RetornoContratoDTO>>> listarArquivos(@RequestParam Integer idEmpresa) {
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Upload arquivo de um relatório referente a um contrato")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Documento salvo"),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "404", description = "Não encontrou o contrato", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @PostMapping(value = "/upload", produces = "application/json", consumes = "application/json")
  @Secured("ROLE_CONSULTOR")
  public ResponseEntity uploadToLocalFileSystem(@RequestParam("file") MultipartFile file, @RequestHeader Integer idContrato) {
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Download arquivo de um relatório referente a um contrato")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Download iniciado"),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "404", description = "Não encontrou o arquivo", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @GetMapping(value = "/download/{fileName:.+}", produces = "application/json", consumes = "application/json")
  @Secured("ROLE_CONSULTOR")
  public ResponseEntity downloadFromDB(@PathVariable String fileName) {
    DownloadContratoDTO dto = new DownloadContratoDTO();
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(""))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
        .body(dto.getArquivo());
  }
}

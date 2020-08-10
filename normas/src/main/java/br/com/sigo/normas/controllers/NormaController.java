package br.com.sigo.normas.controllers;

import br.com.sigo.normas.dtos.ConsultaNormaDTO;
import br.com.sigo.normas.dtos.DetalheNormaDTO;
import br.com.sigo.normas.dtos.DownloadNormaDTO;
import br.com.sigo.normas.dtos.InsereNormaDTO;
import br.com.sigo.normas.dtos.NormasDTO;
import br.com.sigo.normas.exceptions.OrdemInvalidaException;
import br.com.sigo.normas.exceptions.ParamNormaInvalido;
import br.com.sigo.normas.projections.NormaProjection;
import br.com.sigo.normas.response.Response;
import br.com.sigo.normas.services.NormaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("api/norma")
@CrossOrigin(origins = "*")
@Slf4j
public class NormaController {

  @Value("${paginacao.qtd_por_pagina}")
  private int qtdPorPagina;

  @Autowired
  private NormaService normaService;

  //POST devido a limitação do retrofit no android
  @Operation(summary = "Consultar normas")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso"),
      @ApiResponse(responseCode = "204", description = "Nenhuma norma encontrada", content = @Content()),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @PostMapping(produces = "application/json", consumes = "application/json", value = "consultarNormas")
  public ResponseEntity<Response<Page<NormasDTO>>> consultarNormas(@RequestBody ConsultaNormaDTO dto) {
    log.info("consultarNormas - dto: {}", dto.toString());
    Response<Page<NormasDTO>> response = new Response<>();
    try {
      int page = dto.getPage();
      int size = dto.getSize() > 0 ? dto.getSize() : qtdPorPagina;
      String ordem = (dto.getOrdenacao() != null && !dto.getOrdenacao().trim().isEmpty()) ? dto.getOrdenacao() : "ASC";
      String param = (dto.getParam() != null && !dto.getParam().isEmpty()) ? dto.getParam() : "titulo";

      log.debug("consultarNormas - page: {}, size: {}, ordem: {}, param: {}", page, size, ordem, param);
      Page<NormaProjection> todasNormas = normaService.retornarTodasNormas(page, size, ordem, param);
      if (todasNormas != null && !todasNormas.isEmpty()) {
        log.info("consultarNormas - encontrou normas");
        Page<NormasDTO> normasDTO = todasNormas.map(this::getNormasDTO);
        response.setData(normasDTO);
        return ResponseEntity.ok(response);
      }

    } catch (OrdemInvalidaException | ParamNormaInvalido e) {
      log.error("consultarNormas - erro request: ", e);
      response.setErrors(new ArrayList<>());
      response.getErrors().add(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    } catch (Exception e) {
      log.error("consultarNormas - erro interno: ", e);
      response.setErrors(new ArrayList<>());
      response.getErrors().add(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Upload arquivo de um uma norma")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Documento salvo"),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "404", description = "Não encontrou a norma", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @PostMapping(value = "/upload", produces = "application/json", consumes = "application/json")
  public ResponseEntity uploadToLocalFileSystem(@RequestParam("file") MultipartFile file, @RequestHeader Integer idNorma) {
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Download arquivo de um relatório de uma norma")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Download iniciado"),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "404", description = "Não encontrou o arquivo", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @GetMapping(value = "/download/{fileName:.+}", produces = "application/json", consumes = "application/json")
  public ResponseEntity downloadFromDB(@PathVariable String fileName) {
    DownloadNormaDTO dto = new DownloadNormaDTO();
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(""))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
        .body(dto.getArquivo());
  }

  @Operation(summary = "Insere/Atualiza uma norma")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Norma criada/atualizada"),
      @ApiResponse(responseCode = "404", description = "Tipo ou Categoria não encontrado", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @PostMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Response<InsereNormaDTO>> inserirNorma(@Valid @RequestBody InsereNormaDTO dto, BindingResult bindingResult) {
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Detalhar uma norma")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso"),
      @ApiResponse(responseCode = "404", description = "Norma não encontrada", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "400", description = "Ocorreram erros de validação", content = @Content(schema = @Schema(implementation = Response.class))),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @GetMapping(produces = "application/json", consumes = "application/json", value = "detalhar")
  public ResponseEntity<Response<DetalheNormaDTO>> detalhaNorma(@RequestParam Integer norma, BindingResult bindingResult) {
    return ResponseEntity.ok().build();
  }

  private NormasDTO getNormasDTO(NormaProjection n) {
    return new NormasDTO(n.getCategoria(), n.getTipo(), n.getTitulo(), n.getCategoria(), n.getId());
  }
}

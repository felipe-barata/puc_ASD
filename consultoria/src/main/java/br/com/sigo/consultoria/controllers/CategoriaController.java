package br.com.sigo.consultoria.controllers;

import br.com.sigo.consultoria.domain.Categoria;
import br.com.sigo.consultoria.dtos.CategoriaDTO;
import br.com.sigo.consultoria.dtos.PaginacaoDTO;
import br.com.sigo.consultoria.response.Response;
import br.com.sigo.consultoria.services.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

@RestController
@RequestMapping("api/categoria")
@CrossOrigin(origins = "*")
@Slf4j
public class CategoriaController {

  @Value("${paginacao.qtd_por_pagina}")
  private int qtdPorPagina;

  @Autowired
  private CategoriaService categoriaService;

  @Operation(summary = "Insere ou atualiza uma categoria. Para atualizar, um ID deve ser informado")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Inseriu/Atualizou categoria"),
      @ApiResponse(responseCode = "204", description = "Nenhuma informação foi inserida/atualizada"),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @PostMapping(produces = "application/json", consumes = "application/json")
  @Secured("ROLE_USUARIO")
  public ResponseEntity<Response<CategoriaDTO>> atualizarCategoria(@RequestBody @Valid CategoriaDTO dto, BindingResult bindingResult) {
    Response<CategoriaDTO> response = new Response<>();
    if (bindingResult != null && bindingResult.hasErrors()) {
      log.warn("atualizarCategoria - erros de validacao");
      response.setErrors(new ArrayList<>());
      bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
      return ResponseEntity.badRequest().body(response);
    }
    try {
      CategoriaDTO categoria = categoriaService.atualizaCategoria(dto);
      if (categoria != null && categoria.getId() > 0) {
        log.debug("atualizarCategoria - criou/atualizou categoria : {}", dto.getId());
        response.setData(categoria);
        return ResponseEntity.ok(response);
      }
    } catch (Exception e) {
      response.setErrors(new ArrayList<>());
      response.getErrors().add(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    return ResponseEntity.noContent().build();
  }


  //POST por que a API retrofit no android nao suporta body em GET
  @Operation(summary = "Retorna todass as categorias, com paginação")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Retorna todas as categorias conforme paginação"),
      @ApiResponse(responseCode = "204", description = "Não existem categorias para retornar"),
      @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor", content = @Content(schema = @Schema(implementation = Response.class)))
  })
  @PostMapping(produces = "application/json", consumes = "application/json", value = "/listar")
  @Secured("ROLE_USUARIO")
  public ResponseEntity<Response<Page<CategoriaDTO>>> retornaTodasCategorias(@RequestBody PaginacaoDTO pageable) {
    Response<Page<CategoriaDTO>> response = new Response<>();
    try {
      int qtd = pageable.getQtdRegistros() > 0 ? pageable.getQtdRegistros() : qtdPorPagina;

      Page<Categoria> categorias = categoriaService.retornarTodasCategorias(PageRequest.of(pageable.getPagina(), qtd));
      if (categorias != null && !categorias.getContent().isEmpty()) {
        log.info("retornaTodasCategorias - encontrou categorias");
        response.setData(categorias.map(c -> CategoriaDTO.builder().id(c.getId()).descricao(c.getDescricao()).build()));
        return ResponseEntity.ok(response);
      }
    } catch (Exception e) {
      response.setErrors(new ArrayList<>());
      response.getErrors().add(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    return ResponseEntity.noContent().build();
  }

}

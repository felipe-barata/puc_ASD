package br.com.sigo.normas.controllers;

import br.com.sigo.normas.dtos.ConsultaNormaDTO;
import br.com.sigo.normas.dtos.NormasDTO;
import br.com.sigo.normas.exceptions.OrdemInvalidaException;
import br.com.sigo.normas.exceptions.ParamNormaInvalido;
import br.com.sigo.normas.projections.NormaProjection;
import br.com.sigo.normas.response.Response;
import br.com.sigo.normas.services.NormaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @GetMapping
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

  private NormasDTO getNormasDTO(NormaProjection n) {
    return new NormasDTO(n.getCategoria(), n.getTipo(), n.getTitulo(), n.getCategoria());
  }
}

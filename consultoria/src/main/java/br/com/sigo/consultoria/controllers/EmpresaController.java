package br.com.sigo.consultoria.controllers;

import br.com.sigo.consultoria.dtos.AtribuirCategoriaEmpresaDTO;
import br.com.sigo.consultoria.dtos.EmpresaDTO;
import br.com.sigo.consultoria.exceptions.CategoriaNaoEncontradaException;
import br.com.sigo.consultoria.exceptions.EmpresaNaoEncontradaException;
import br.com.sigo.consultoria.response.Response;
import br.com.sigo.consultoria.services.EmpresaService;
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

@RestController
@RequestMapping("api/empresa")
@CrossOrigin(origins = "*")
@Slf4j
public class EmpresaController {

  @Autowired
  private EmpresaService empresaService;

  @PostMapping
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

  @PostMapping(value = "atribuirCategoria")
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

}

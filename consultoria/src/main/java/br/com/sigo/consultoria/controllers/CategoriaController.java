package br.com.sigo.consultoria.controllers;

import br.com.sigo.consultoria.dtos.CategoriaDTO;
import br.com.sigo.consultoria.response.Response;
import br.com.sigo.consultoria.services.CategoriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
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

  @Autowired
  private CategoriaService categoriaService;

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

}

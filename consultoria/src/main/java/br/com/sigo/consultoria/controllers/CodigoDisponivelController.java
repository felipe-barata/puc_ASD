package br.com.sigo.consultoria.controllers;

import br.com.sigo.consultoria.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/codigoDisponivel")
@CrossOrigin(origins = "*")
@Slf4j
public class CodigoDisponivelController {

  @Autowired
  private UsuarioService usuarioService;

  @Operation(summary = "Verifica se o código informado está atribuido a um usuário")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Código pertence a um usuário"),
      @ApiResponse(responseCode = "204", description = "Código não pertence a um usuário")
  })
  @GetMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity verificaCodigoUsuario(@RequestParam("codigo") String codigo) {
    log.info("verificaCodigoUsuario - codigo: {}", codigo);
    if (usuarioService.buscarUsername(codigo).isPresent()) {
      log.debug("verificaCodigoUsuario - codigo ja existe: {}", codigo);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.noContent().build();
  }

}

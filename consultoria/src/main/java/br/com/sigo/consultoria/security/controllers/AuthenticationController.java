package br.com.sigo.consultoria.security.controllers;

import br.com.sigo.consultoria.enums.PerfilEnum;
import br.com.sigo.consultoria.response.Response;
import br.com.sigo.consultoria.security.dto.JwtAuthenticationDto;
import br.com.sigo.consultoria.security.dto.TokenDto;
import br.com.sigo.consultoria.security.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

  private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
  private static final String TOKEN_HEADER = "Authorization";
  private static final String BEARER_PREFIX = "Bearer ";

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private UserDetailsService userDetailsService;

  /**
   * Gera e retorna um novo token JWT.
   *
   * @param authenticationDto
   * @param result
   * @return ResponseEntity<Response < TokenDto>>
   * @throws AuthenticationException
   */
  @PostMapping
  public ResponseEntity<Response<TokenDto>> gerarTokenJwt(
      @Valid @RequestBody JwtAuthenticationDto authenticationDto, BindingResult result)
      throws AuthenticationException {
    Response<TokenDto> response = new Response<TokenDto>();

    if (result.hasErrors()) {
      log.error("gerarTokenJwt - Erro validando usuario: {}", result.getAllErrors());
      response.setErrors(new ArrayList<>());
      result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
      return ResponseEntity.badRequest().body(response);
    }

    log.info("gerarTokenJwt - Gerando token para o codigo {}.", authenticationDto.getCodigo());
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        authenticationDto.getCodigo(), authenticationDto.getSenha()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(authenticationDto.getCodigo()));

    List<PerfilEnum> perfis = new ArrayList<>();
    userDetails.getAuthorities().forEach(auth -> perfis.add(PerfilEnum.valueOf(auth.getAuthority())));

    String token = jwtTokenUtil.obterToken(userDetails);
    response.setData(new TokenDto(token, perfis));

    return ResponseEntity.ok(response);
  }

  /**
   * Gera um novo token com uma nova data de expiração.
   *
   * @param request
   * @return ResponseEntity<Response < TokenDto>>
   */
  @PostMapping(value = "/refresh")
  public ResponseEntity<Response<TokenDto>> gerarRefreshTokenJwt(HttpServletRequest request) {
    log.info("gerarRefreshTokenJwt - Gerando refresh token JWT.");
    Response<TokenDto> response = new Response<TokenDto>();
    Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));

    List<String> erros = new ArrayList<>();

    if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
      token = Optional.of(token.get().substring(7));
    }

    if (token.isEmpty()) {
      log.warn("gerarRefreshTokenJwt - token nao informado");
      erros.add("Token não informado.");
    } else if (!jwtTokenUtil.tokenValido(token.get())) {
      log.warn("gerarRefreshTokenJwt - token inválido ou expirado");
      erros.add("Token inválido ou expirado.");
    }

    List<PerfilEnum> perfis = new ArrayList<>();

    String usernameFromToken = jwtTokenUtil.getUsernameFromToken(token.orElse(""));
    if (usernameFromToken == null || usernameFromToken.trim().isEmpty()) {
      log.warn("gerarRefreshTokenJwt - username nao encontrado");
      erros.add("Username não encontrado no token");
    } else {
      UserDetails userDetails = userDetailsService.loadUserByUsername(usernameFromToken);
      if (userDetails != null) {
        userDetails.getAuthorities().forEach(auth -> perfis.add(PerfilEnum.valueOf(auth.getAuthority())));
      } else {
        log.warn("gerarRefreshTokenJwt - usuario {} nao encontrado", usernameFromToken);
        erros.add(MessageFormat.format("Usuario: {0} não encontrado", usernameFromToken));
      }
    }

    if (!erros.isEmpty()) {
      response.setErrors(erros);
      return ResponseEntity.badRequest().body(response);
    }

    String refreshedToken = jwtTokenUtil.refreshToken(token.get());
    response.setData(new TokenDto(refreshedToken, perfis));
    return ResponseEntity.ok(response);
  }

}

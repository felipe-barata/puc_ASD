package br.com.sigo.consultoria.controllers;

import br.com.sigo.consultoria.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TestUsuarioController {

  @MockBean
  private UsuarioService usuarioService;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;
}

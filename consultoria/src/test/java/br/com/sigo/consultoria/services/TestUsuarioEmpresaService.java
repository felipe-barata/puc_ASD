package br.com.sigo.consultoria.services;

import br.com.sigo.consultoria.domain.Empresa;
import br.com.sigo.consultoria.domain.Usuario;
import br.com.sigo.consultoria.domain.UsuarioEmpresa;
import br.com.sigo.consultoria.dtos.UsuarioEmpresaDTO;
import br.com.sigo.consultoria.exceptions.ConsultoriaException;
import br.com.sigo.consultoria.repository.EmpresaRepository;
import br.com.sigo.consultoria.repository.UsuarioEmpresaRepository;
import br.com.sigo.consultoria.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestUsuarioEmpresaService {

  @MockBean
  private UsuarioRepository usuarioRepository;

  @MockBean
  private EmpresaRepository empresaRepository;

  @MockBean
  private UsuarioEmpresaRepository usuarioEmpresaRepository;

  @Autowired
  private UsuarioEmpresaService usuarioEmpresaService;

  @Test
  public void testAtualizarUsuarioEmpresa() throws ConsultoriaException {
    BDDMockito.when(usuarioRepository.findById(1)).thenReturn(Optional.of(new Usuario()));
    BDDMockito.when(usuarioRepository.findById(2)).thenReturn(Optional.of(new Usuario()));
    BDDMockito.when(empresaRepository.findById(1)).thenReturn(Optional.of(new Empresa()));
    BDDMockito.when(usuarioEmpresaRepository.save(Mockito.any(UsuarioEmpresa.class))).thenReturn(new UsuarioEmpresa());
    Assertions.assertNotNull(usuarioEmpresaService.atualizarUsuarioEmpresa(getLista()));
  }

  private List<UsuarioEmpresaDTO> getLista() {
    List<UsuarioEmpresaDTO> l = new ArrayList<>();
    l.add(UsuarioEmpresaDTO.builder().idEmpresa(1).idUsuario(1).build());
    l.add(UsuarioEmpresaDTO.builder().idEmpresa(1).idUsuario(2).build());
    return l;
  }

}

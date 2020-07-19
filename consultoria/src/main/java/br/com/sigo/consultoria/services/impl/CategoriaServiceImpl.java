package br.com.sigo.consultoria.services.impl;

import br.com.sigo.consultoria.domain.Categoria;
import br.com.sigo.consultoria.dtos.CategoriaDTO;
import br.com.sigo.consultoria.exceptions.ConsultoriaException;
import br.com.sigo.consultoria.repository.CategoriaRepository;
import br.com.sigo.consultoria.services.CategoriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CategoriaServiceImpl implements CategoriaService {

  @Autowired
  private CategoriaRepository categoriaRepository;

  @Override
  public CategoriaDTO atualizaCategoria(CategoriaDTO dto) throws ConsultoriaException {
    log.info("atualizaCategoria - categoria: {}", dto.getDescricao());
    try {
      Categoria categoria = Categoria.builder().descricao(dto.getDescricao()).build();
      Optional<Categoria> optionalCategoria = categoriaRepository.findById(dto.getId());
      optionalCategoria.ifPresent(value -> categoria.setId(value.getId()));
      Categoria cat = categoriaRepository.save(categoria);
      if (cat != null) {
        dto.setId(cat.getId());
      }
      return dto;
    } catch (Exception e) {
      log.error("atualizaCategoria - erro: ", e);
      throw new ConsultoriaException(e.getMessage());
    }
  }

}

package br.com.sigo.consultoria.services;

import br.com.sigo.consultoria.domain.Categoria;
import br.com.sigo.consultoria.dtos.CategoriaDTO;
import br.com.sigo.consultoria.exceptions.ConsultoriaException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaService {

  CategoriaDTO atualizaCategoria(CategoriaDTO dto) throws ConsultoriaException;

  Page<Categoria> retornarTodasCategorias(Pageable pageable);
}

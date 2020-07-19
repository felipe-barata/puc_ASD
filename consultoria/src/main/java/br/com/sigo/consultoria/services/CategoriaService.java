package br.com.sigo.consultoria.services;

import br.com.sigo.consultoria.dtos.CategoriaDTO;
import br.com.sigo.consultoria.exceptions.ConsultoriaException;

public interface CategoriaService {

  CategoriaDTO atualizaCategoria(CategoriaDTO dto) throws ConsultoriaException;

}

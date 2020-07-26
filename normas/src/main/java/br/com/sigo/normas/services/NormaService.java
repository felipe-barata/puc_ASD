package br.com.sigo.normas.services;

import br.com.sigo.normas.exceptions.NormasException;
import br.com.sigo.normas.projections.NormaProjection;
import org.springframework.data.domain.Page;

public interface NormaService {

  Page<NormaProjection> retornarTodasNormas(Integer page, Integer size, String ordenacao, String param) throws NormasException;

}

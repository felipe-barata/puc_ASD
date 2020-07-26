package br.com.sigo.normas.services.impl;

import br.com.sigo.normas.enums.NormasParamsEnum;
import br.com.sigo.normas.exceptions.NormasException;
import br.com.sigo.normas.exceptions.OrdemInvalidaException;
import br.com.sigo.normas.exceptions.ParamNormaInvalido;
import br.com.sigo.normas.projections.NormaProjection;
import br.com.sigo.normas.repository.NormaRepository;
import br.com.sigo.normas.services.NormaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NormaServiceImpl implements NormaService {

  @Autowired
  private NormaRepository normaRepository;

  @Override
  public Page<NormaProjection> retornarTodasNormas(Integer page, Integer size, String ordenacao, String param) throws NormasException {
    log.info("retornarTodasNormas - page: {}, size: {}, ordenacao: {}, param: {}", page, size, ordenacao, param);
    try {
      Sort.Direction sort = Sort.Direction.fromString(ordenacao);
      if (sort == null) {
        log.warn("retornarTodasNormas - ordenacao invalida");
        throw new OrdemInvalidaException(ordenacao);
      }

      if (NormasParamsEnum.get(param) == null) {
        log.warn("retornarTodasNormas - parametro invalida");
        throw new ParamNormaInvalido(param);
      }
      PageRequest pageRequest = PageRequest.of(page, size, sort, param);
      return normaRepository.selectTodasNormas(pageRequest);
    } catch (NormasException e) {
      throw e;
    } catch (IllegalArgumentException e) {
      log.warn("retornarTodasNormas - ordenacao invalida");
      throw new OrdemInvalidaException(ordenacao);
    } catch (Exception e) {
      log.error("retornarTodasNormas - Erro: ", e);
      throw new NormasException(e.getMessage());
    }
  }
}

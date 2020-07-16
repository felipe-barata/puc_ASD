package br.com.sigo.consultoria.repository;

import br.com.sigo.consultoria.domain.ContratoIntegracoes;
import br.com.sigo.consultoria.domain.chave.ContratoIntegracoesPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoIntegracoesRepository extends JpaRepository<ContratoIntegracoes, ContratoIntegracoesPK> {
}

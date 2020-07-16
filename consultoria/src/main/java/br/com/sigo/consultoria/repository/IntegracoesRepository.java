package br.com.sigo.consultoria.repository;

import br.com.sigo.consultoria.domain.Integracoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntegracoesRepository extends JpaRepository<Integracoes, Long> {
}

package br.com.sigo.normas.repository;

import br.com.sigo.normas.domain.TipoNorma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoNormaRepository extends JpaRepository<TipoNorma, Integer> {
}

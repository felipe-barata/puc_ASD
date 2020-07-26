package br.com.sigo.normas.repository;

import br.com.sigo.normas.domain.CategoriaNorma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaNormaRepository extends JpaRepository<CategoriaNorma, Integer> {
}

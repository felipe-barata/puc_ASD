package br.com.sigo.consultoria.repository;

import br.com.sigo.consultoria.domain.Relatorios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatoriosRepository extends JpaRepository<Relatorios, Integer> {
}

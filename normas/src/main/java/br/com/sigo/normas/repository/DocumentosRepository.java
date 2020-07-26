package br.com.sigo.normas.repository;

import br.com.sigo.normas.domain.Documentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentosRepository extends JpaRepository<Documentos, Integer> {
}

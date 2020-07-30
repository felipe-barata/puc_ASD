package br.com.sigo.consultoria.repository;

import br.com.sigo.consultoria.domain.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

  Page<Categoria> findAll(Pageable pageable);

}

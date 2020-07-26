package br.com.sigo.normas.repository;

import br.com.sigo.normas.domain.Norma;
import br.com.sigo.normas.projections.NormaProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NormaRepository extends JpaRepository<Norma, Integer> {

  @Query(value = "select c.descricao categoria, t.descricao tipo, n.titulo, n.norma from norma n inner join categoria_norma c on (n.categoria_norma_id = c.id) inner join tipo_norma t on (n.tipo_norma_id = t.id)", nativeQuery = true, countQuery = "select count(n.norma) from norma n inner join categoria_norma c on (n.categoria_norma_id = c.id) inner join tipo_norma t on (n.tipo_norma_id = t.id)")
  Page<NormaProjection> selectTodasNormas(Pageable pageable);

}

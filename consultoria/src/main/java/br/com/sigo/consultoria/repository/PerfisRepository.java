package br.com.sigo.consultoria.repository;

import br.com.sigo.consultoria.domain.Perfis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PerfisRepository extends JpaRepository<Perfis, Long> {

  @Query(value = "select * from perfis where usuario_id = :usuario", nativeQuery = true)
  List<Perfis> retornaPerfisUsuario(@Param(value = "usuario") Integer codigoUsuario);

  @Modifying
  @Transactional
  @Query(value = "delete from perfis where usuario_id = :usuario", nativeQuery = true)
  void apagaPerfilUsuario(@Param(value = "usuario") Integer codigoUsuario);
}

package br.com.sigo.consultoria.repository;

import br.com.sigo.consultoria.domain.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

  Optional<Empresa> findByCnpj(String cnpj);

  @Transactional
  @Modifying
  @Query(value = "update empresa set categoria_id = :codigoCategoria where cnpj = :cnpj", nativeQuery = true)
  Integer atualizaCodigoCategoriaEmpresa(@Param("codigoCategoria") Integer codigoCategoria, @Param("cnpj") String cnpj);

}

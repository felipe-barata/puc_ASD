package br.com.sigo.consultoria.repository;

import br.com.sigo.consultoria.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

  Optional<Usuario> findByCodigo(Integer codigo);

  @Transactional
  @Modifying
  @Query(value = "update usuario set ativo = :inativa where codigo = :codigo", nativeQuery = true)
  Integer ativaInativaUsuario(@Param("codigo") Integer codigo, @Param("inativa") Boolean inativa);

}

package br.com.sigo.consultoria.repository;

import br.com.sigo.consultoria.domain.UsuarioEmpresa;
import br.com.sigo.consultoria.domain.chave.UsuarioEmpresaPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioEmpresaRepository extends JpaRepository<UsuarioEmpresa, UsuarioEmpresaPK> {
}

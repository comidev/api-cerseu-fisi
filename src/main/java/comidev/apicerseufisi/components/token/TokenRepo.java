package comidev.apicerseufisi.components.token;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comidev.apicerseufisi.components.usuario.Usuario;

@Repository
public interface TokenRepo extends JpaRepository<TokenConfirmacion, Long> {
    Optional<TokenConfirmacion> findByToken(String token);

    List<TokenConfirmacion> findByUsuario(Usuario usuario);
}

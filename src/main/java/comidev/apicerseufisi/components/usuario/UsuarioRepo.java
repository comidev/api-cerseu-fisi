package comidev.apicerseufisi.components.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comidev.apicerseufisi.components.usuario.utils.Rol;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);

    List<Usuario> findByRol(Rol rol);

    boolean existsByCorreo(String correo);
}

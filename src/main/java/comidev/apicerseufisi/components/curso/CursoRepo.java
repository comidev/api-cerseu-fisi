package comidev.apicerseufisi.components.curso;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comidev.apicerseufisi.components.curso.utils.CursoEstado;

@Repository
public interface CursoRepo extends JpaRepository<Curso, Long> {
    Optional<Curso> findByCodigo(String codigo);

    List<Curso> findByCursoEstado(CursoEstado cursoEstado);

    
}

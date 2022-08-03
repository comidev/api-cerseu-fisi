package comidev.apicerseufisi.components.disponibilidad;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import comidev.apicerseufisi.components.curso.Curso;
import comidev.apicerseufisi.components.docente.Docente;

public interface DisponibilidadRepo extends JpaRepository<Disponibilidad, Long> {
    Optional<Disponibilidad> findByDocenteAndCurso(Docente docente, Curso curso);

    List<Disponibilidad> findByDocente(Docente docente);

    List<Disponibilidad> findByCurso(Curso curso);
}

package comidev.apicerseufisi.components.horario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import comidev.apicerseufisi.components.aula.Aula;
import comidev.apicerseufisi.components.disponibilidad.Disponibilidad;
import comidev.apicerseufisi.components.docente.Docente;
import comidev.apicerseufisi.components.horario.utils.HorarioEstado;
import comidev.apicerseufisi.utils.Dia;

@Repository
public interface HorarioRepo extends JpaRepository<Horario, Long> {
    Optional<Horario> findByDisponibilidad(Disponibilidad disponibilidad);

    Optional<Horario> findByDisponibilidadAndHorarioEstado(Disponibilidad disponibilidad, HorarioEstado horarioEstado);

    List<Horario> findByDiaAndAulaIsNotNull(Dia dia);

    List<Horario> findAllByAulaIsNotNull();

    List<Horario> findByAula(Aula aula);

    List<Horario> findByHorarioEstado(HorarioEstado horarioEstado);

    @Modifying
    @Query("UPDATE Horario h SET h.horarioEstado = ?1 WHERE h.horarioEstado = ?2")
    void updateEstadoFor(HorarioEstado estado, HorarioEstado find);

    @Query("SELECT ho FROM Disponibilidad di INNER JOIN Horario ho ON ho.disponibilidad = di.id WHERE di.docente = ?1 AND ho.horarioEstado = ?2")
    List<Horario> findByDocenteForEstado(Docente docente, HorarioEstado estado);
}

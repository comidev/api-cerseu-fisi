package comidev.apicerseufisi.components.pago;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import comidev.apicerseufisi.components.alumno.Alumno;
import comidev.apicerseufisi.components.curso.Curso;
import comidev.apicerseufisi.components.curso.utils.CursoEstado;
import comidev.apicerseufisi.components.horario.Horario;
import comidev.apicerseufisi.components.pago.utils.PagoEstado;
import comidev.apicerseufisi.components.solicitud.Solicitud;

@Repository
public interface PagoRepo extends JpaRepository<Pago, Long> {
    List<Pago> findByCursoCodigoAndPagoEstado(String cursoCodigo, PagoEstado estado);

    List<Pago> findBySolicitudAndPagoEstado(Solicitud solicitud, PagoEstado estado);

    List<Pago> findByPagoEstado(PagoEstado estado);

    List<Pago> findBySolicitud(Solicitud solicitud);

    @Query("SELECT ho FROM Solicitud so INNER JOIN Pago pa ON pa.solicitud = so.id INNER JOIN Horario ho ON ho.id = pa.horario INNER JOIN Disponibilidad dis ON ho.disponibilidad = dis.id INNER JOIN Curso cu ON cu.codigo = pa.cursoCodigo WHERE so.alumno = ?1 AND pa.pagoEstado = ?2")
    List<Horario> findHorarioByAlumnoAndEstado(Alumno alumno, PagoEstado estado);

    @Query("SELECT cu FROM Pago pa INNER JOIN Curso cu ON pa.cursoCodigo = cu.codigo WHERE pa.pagoEstado = ?1")
    List<Curso> findCursoForPagoEstado(PagoEstado estado);

    @Query("SELECT cu FROM Curso cu WHERE cu.codigo = ?1")
    Optional<Curso> findCursoByCodigo(String codigo);

    @Modifying
    @Query("UPDATE Pago AS pa SET pa.pagoEstado = ?1 WHERE pa.pagoEstado = ?2 AND pa.cursoCodigo IN (SELECT cu.codigo FROM Curso cu WHERE cu.cursoEstado = ?3)")
    void updateEstadoByEstadoAndCursoEstado(PagoEstado set, PagoEstado find,
            CursoEstado curso);

    @Modifying
    @Query("UPDATE Pago AS pa SET pa.pagoEstado = ?1 WHERE pa.pagoEstado = ?2")
    void updateEstadoFor(PagoEstado set, PagoEstado where);
}

package comidev.apicerseufisi.components.solicitud;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comidev.apicerseufisi.components.alumno.Alumno;

@Repository
public interface SolicitudRepo extends JpaRepository<Solicitud, Long> {

    List<Solicitud> findByAlumno(Alumno alumno);
}

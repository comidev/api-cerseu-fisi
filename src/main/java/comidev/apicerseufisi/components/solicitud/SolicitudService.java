package comidev.apicerseufisi.components.solicitud;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

import comidev.apicerseufisi.components.alumno.Alumno;
import comidev.apicerseufisi.components.alumno.AlumnoService;
import comidev.apicerseufisi.components.solicitud.request.SolicitudCreate;
import comidev.apicerseufisi.components.solicitud.response.SolicitudByAlumno;

@Service
@AllArgsConstructor
public class SolicitudService {
    private final SolicitudRepo solicitudRepo;
    private final AlumnoService alumnoService;

    public Solicitud registrarSolicitud(SolicitudCreate solicitud) {
        // * Validamos Alumno
        Alumno alumnoDB = alumnoService.findById(solicitud.getAlumnoId());
        return solicitudRepo.save(new Solicitud(alumnoDB, solicitud.getCodigo()));
    }

    public List<SolicitudByAlumno> getAllOrByAlumno(Long alumnoId) {
        if (alumnoId != null) {
            return this.findSolicitudAllByAlumno(alumnoId).stream()
                    .map(SolicitudByAlumno::new)
                    .toList();
        }
        return solicitudRepo.findAll().stream()
                .map(SolicitudByAlumno::new)
                .toList();
    }

    public List<Solicitud> findSolicitudAllByAlumno(Long alumnoId) {
        return solicitudRepo.findByAlumno(new Alumno(alumnoId));
    }
}

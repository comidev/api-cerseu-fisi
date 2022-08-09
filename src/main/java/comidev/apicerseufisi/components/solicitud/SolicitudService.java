package comidev.apicerseufisi.components.solicitud;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import comidev.apicerseufisi.components.alumno.Alumno;
import comidev.apicerseufisi.components.alumno.AlumnoService;
import comidev.apicerseufisi.components.solicitud.request.SolicitudCreate;
import comidev.apicerseufisi.components.solicitud.response.SolicitudByAlumno;
import comidev.apicerseufisi.components.solicitud.response.SolicitudList;

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

    public List<SolicitudList> getAll() {
        return solicitudRepo.findAll().stream()
                .map(SolicitudList::new)
                .collect(Collectors.toList());
    }

    public List<SolicitudByAlumno> getAllByAlumno(Long alumnoId) {
        return this.findSolicitudAllByAlumno(alumnoId).stream()
                .map(SolicitudByAlumno::new)
                .collect(Collectors.toList());
    }

    public List<Solicitud> findSolicitudAllByAlumno(Long alumnoId) {
        return solicitudRepo.findByAlumno(new Alumno(alumnoId));
    }
}

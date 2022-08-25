package comidev.apicerseufisi.components.solicitud;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import comidev.apicerseufisi.components.solicitud.doc.SolicitudDoc;
import comidev.apicerseufisi.components.solicitud.response.SolicitudByAlumno;
import comidev.apicerseufisi.components.solicitud.response.SolicitudList;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/solicitudes")
@AllArgsConstructor
public class SolicitudController implements SolicitudDoc {
    private final SolicitudService solicitudService;

    @GetMapping("/alumno/{alumnoId}")
    @ResponseBody
    public List<SolicitudByAlumno> getAllSolicitudesByAlumno(
            @PathVariable Long alumnoId) {
        return solicitudService.getAllSolicitudesByAlumno(alumnoId);
    }

    @GetMapping
    @ResponseBody
    public List<SolicitudList> getAllSolicitudes() {
        return solicitudService.getAllSolicitudes();
    }
}

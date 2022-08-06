package comidev.apicerseufisi.components.solicitud;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import comidev.apicerseufisi.components.solicitud.response.SolicitudByAlumno;
import comidev.apicerseufisi.components.solicitud.response.SolicitudList;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/solicitudes")
@AllArgsConstructor
public class SolicitudController {
    private final SolicitudService solicitudService;

    @Operation(summary = "Devuelve las solicitudes del sistema", description = "Util para mostrar las solicitudes del sistema")
    @GetMapping
    public ResponseEntity<List<SolicitudList>> getAll() {
        List<SolicitudList> body = solicitudService.getAll();
        return ResponseEntity.status(body.isEmpty() ? 204 : 200).body(body);
    }

    @Operation(summary = "Devuelve las solicitudes del sistema por alumno", description = "Util para mostrar las solicitudes del sistema por alumno")
    @GetMapping("/alumno/{alumnoId}")
    public ResponseEntity<List<SolicitudByAlumno>> getAllByAlumno(
            @PathVariable Long alumnoId) {
        List<SolicitudByAlumno> body = solicitudService.getAllByAlumno(alumnoId);
        return ResponseEntity.status(body.isEmpty() ? 204 : 200).body(body);
    }
}

package comidev.apicerseufisi.components.solicitud;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import comidev.apicerseufisi.components.solicitud.response.SolicitudByAlumno;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/solicitudes")
@AllArgsConstructor
public class SolicitudController {
    private final SolicitudService solicitudService;

    @GetMapping
    public ResponseEntity<List<SolicitudByAlumno>> getAllOrByAlumno(
            @RequestParam(name = "alumnoId", required = false) Long alumnoId) {
        List<SolicitudByAlumno> body = solicitudService.getAllOrByAlumno(alumnoId);
        return ResponseEntity.status(body.isEmpty() ? 204 : 200).body(body);
    }
}

package comidev.apicerseufisi.components.solicitud;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import comidev.apicerseufisi.components.alumno.Alumno;
import comidev.apicerseufisi.config.ApiIntegrationTest;
import comidev.apicerseufisi.services.Fabric;
import comidev.apicerseufisi.services.Request;
import comidev.apicerseufisi.services.Response;

// * PATH: /solicitudes
@ApiIntegrationTest
public class SolicitudControllerTest {
    @Autowired
    private Request request;
    @Autowired
    private Fabric fabric;

    // * GET ->
    @Test
    void OK_CuandoDevuelveLasSolicitudesDeLosAlumnos_getAll()
            throws Exception {
        // Arreglar
        Solicitud solicitud = fabric.createSolicitud(null, null);

        // Actuar
        Response response = request.get("/solicitudes").send();

        // Afirmar
        response.isStatus(HttpStatus.OK);
        assertTrue(response.bodyContains(solicitud.getCodigo()));
    }

    // * GET -> ?alumnoId
    @Test
    void OK_CuandoDevuelveLasSolicitudesDelAlumno_getAllByAlumno()
            throws Exception {
        // Arreglar
        Alumno alumno = fabric.createAlumno(null);
        Solicitud solicitud = fabric.createSolicitud(alumno, null);

        // Actuar
        Response response = request.get("/solicitudes/alumno/" + alumno.getId())
                .send();

        // Afirmar
        response.isStatus(HttpStatus.OK);
        assertTrue(response.bodyContains(solicitud.getCodigo(), solicitud.getId()));
    }
}

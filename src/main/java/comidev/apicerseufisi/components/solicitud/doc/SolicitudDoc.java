package comidev.apicerseufisi.components.solicitud.doc;

import java.util.List;

import comidev.apicerseufisi.components.solicitud.response.SolicitudByAlumno;
import comidev.apicerseufisi.components.solicitud.response.SolicitudList;
import io.swagger.v3.oas.annotations.Operation;

public interface SolicitudDoc {
    @Operation(summary = """
                Devuelve las solicitudes del sistema
            """, description = """
            Util para mostrar las solicitudes del sistema
            """)
    List<SolicitudList> getAllSolicitudes();

    @Operation(summary = """
                Devuelve las solicitudes del sistema por alumno
            """, description = """
            Util para mostrar las solicitudes del sistema por alumno
            """)
    List<SolicitudByAlumno> getAllSolicitudesByAlumno(Long alumnoId);
}

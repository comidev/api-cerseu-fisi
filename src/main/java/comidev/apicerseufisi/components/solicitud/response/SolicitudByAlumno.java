package comidev.apicerseufisi.components.solicitud.response;

import java.sql.Timestamp;

import comidev.apicerseufisi.components.solicitud.Solicitud;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SolicitudByAlumno {
    private Long id;
    private String codigo;
    private Timestamp createdAt;

    public SolicitudByAlumno(Solicitud solicitud) {
        this.id = solicitud.getId();
        this.codigo = solicitud.getCodigo();
        this.createdAt = Timestamp.valueOf(solicitud.getCreatedAt());
    }
}

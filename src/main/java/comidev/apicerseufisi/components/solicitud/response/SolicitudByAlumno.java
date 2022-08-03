package comidev.apicerseufisi.components.solicitud.response;

import java.time.LocalDateTime;

import comidev.apicerseufisi.components.solicitud.Solicitud;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SolicitudByAlumno {
    private Long id;
    private String codigo;
    private LocalDateTime createdAt;

    public SolicitudByAlumno(Solicitud solicitud) {
        this.id = solicitud.getId();
        this.codigo = solicitud.getCodigo();
        this.createdAt = solicitud.getCreatedAt();
    }
}

package comidev.apicerseufisi.components.solicitud.response;

import java.sql.Timestamp;

import comidev.apicerseufisi.components.solicitud.Solicitud;
import comidev.apicerseufisi.components.usuario.response.UsuarioList;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SolicitudList {
    private Long id;
    private String codigo;
    private UsuarioList alumno;
    private Timestamp createdAt;

    public SolicitudList(Solicitud solicitud) {
        this.id = solicitud.getId();
        this.codigo = solicitud.getCodigo();
        this.alumno = new UsuarioList(solicitud.getAlumno());
        this.createdAt = Timestamp.valueOf(solicitud.getCreatedAt());
    }
}

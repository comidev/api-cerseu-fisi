package comidev.apicerseufisi.components.notificacion.request;

import comidev.apicerseufisi.components.contenido.request.ContenidoCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionCreate {
    private String toEmail;
    private String tema;
    private ContenidoCreate contenido;
}

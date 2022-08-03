package comidev.apicerseufisi.components.notificacion;

import org.springframework.stereotype.Service;

import comidev.apicerseufisi.components.notificacion.request.NotificacionCreate;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificacionService {
    private final NotificacionRepo notificaionRepo;

    public void registrarNotificacion(NotificacionCreate notificacionCreate) {
        Notificacion notificacion = new Notificacion(notificacionCreate);
        notificaionRepo.save(notificacion);
    }
}

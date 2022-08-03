package comidev.apicerseufisi.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import comidev.apicerseufisi.components.notificacion.request.NotificacionCreate;
import comidev.apicerseufisi.email.EmailSenderService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Consumer {
    private final EmailSenderService emailSenderService;

    @RabbitListener(queues = { "${comidev.queue.email}" })
    private void recieve(NotificacionCreate message) {
        emailSenderService.send(message);
    }
}

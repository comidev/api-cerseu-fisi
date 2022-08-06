package comidev.apicerseufisi.components.token;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import comidev.apicerseufisi.components.contenido.request.ContenidoCreate;
import comidev.apicerseufisi.components.notificacion.request.NotificacionCreate;
import comidev.apicerseufisi.components.usuario.Usuario;
import comidev.apicerseufisi.exceptions.HttpException;
import comidev.apicerseufisi.rabbitmq.Producer;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TokenService {
    private final TokenRepo tokenRepo;
    private final Producer producer;
    private final static String LINK_TOKEN = "http://localhost:8080/auths/confirm?token=";

    public void sendToken(Usuario usuario) {
        String token = UUID.randomUUID().toString();
        tokenRepo.save(new TokenConfirmacion(token, usuario));
        // * Enviamos a RabbitMQ
        ContenidoCreate contenido = new ContenidoCreate(
                "Confirma tu Correo :D",
                "Hola " + usuario.getNombre(),
                "Gracias por registrarte. Por favor, dé click en el "
                        + "siguiente enlace para activar su cuenta:",
                LINK_TOKEN + token,
                "Activar ahora",
                "Link expirará en 15 minutos. Buen día.");
        NotificacionCreate body = new NotificacionCreate(
                usuario.getCorreo(),
                "Activar correo - Cerseu FISI",
                contenido);
        producer.send(body);
    }

    public String confirmToken(String token) {
        TokenConfirmacion confirmationToken = this.findByToken(token);
        if (confirmationToken.isConfirmed()) {
            String message = "El token ya esta confirmado wtf!";
            throw new HttpException(HttpStatus.BAD_REQUEST, message);
        }

        if (confirmationToken.expired()) {
            String message = "Token expirado. Vuelva a iniciar sesión.";
            throw new HttpException(HttpStatus.UNAUTHORIZED, message);
        }

        confirmationToken.confirmar();
        tokenRepo.save(confirmationToken);
        String correo = confirmationToken.getUsuario().getCorreo();
        return correo;
    }

    private TokenConfirmacion findByToken(String token) {
        return tokenRepo.findByToken(token).orElseThrow(() -> {
            String message = "El token es invalido";
            return new HttpException(HttpStatus.UNAUTHORIZED, message);
        });
    }

    public boolean resendToken(Usuario user) {
        List<TokenConfirmacion> tokens = tokenRepo.findByUsuario(user);
        if (tokens.isEmpty()) {
            String message = "El usuario no tiene tokens wtf!";
            throw new HttpException(HttpStatus.UNAUTHORIZED, message);
        }

        TokenConfirmacion lastToken = tokens.get(tokens.size() - 1);
        boolean expired = lastToken.expired();
        if (expired) {
            this.sendToken(user);
        }
        return expired;
    }
}

package comidev.apicerseufisi.components.usuario.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;

@Getter
public class UsuarioLogin {
    @NotEmpty(message = "No puede ser vacio")
    @Email(message = "No tiene formato email")
    private String correo;

    @NotEmpty(message = "No puede ser vacio")
    private String password;
}

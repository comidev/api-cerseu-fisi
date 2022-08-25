package comidev.apicerseufisi.components.auth.doc;

import comidev.apicerseufisi.components.usuario.request.UsuarioCreate;
import comidev.apicerseufisi.components.usuario.request.UsuarioLogin;
import comidev.apicerseufisi.jwt.Tokens;
import io.swagger.v3.oas.annotations.Operation;

public interface AuthDoc {
    @Operation(summary = "Iniciar sesión con username (correo) y password", description = "Inicia sesión previamente debió activar su cuenta con un enlace que se le envió a su correo")
    Tokens login(UsuarioLogin body);

    @Operation(summary = "Registra a un usuario, ojo con el Rol!!", description = "Cuando se registre al sistema, se le enviará un link de activación a su correo")
    void registrar(UsuarioCreate body);

    @Operation(summary = "Confirma el usuario y podrá hacer login", description = "Esto lo usará el link que se enviará al usuario en su correo")
    void confirmToken(String token);
}

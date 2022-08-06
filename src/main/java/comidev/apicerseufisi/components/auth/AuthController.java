package comidev.apicerseufisi.components.auth;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import comidev.apicerseufisi.components.usuario.request.UsuarioCreate;
import comidev.apicerseufisi.components.usuario.request.UsuarioLogin;
import comidev.apicerseufisi.exceptions.Validator;
import comidev.apicerseufisi.jwt.Tokens;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auths")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Iniciar sesión con username (correo) y pasword", description = "Inicia sesión previamente debió activar su cuenta con un enlace que se le envió a su correo")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody()
    public Tokens login(
            @Valid @RequestBody UsuarioLogin usuario,
            BindingResult bindingResult) {
        Validator.checkValidBody(bindingResult);
        return authService.login(usuario);
    }

    @Operation(summary = "Registra a un usuario, ojo con el Rol!!", description = "Cuando se registre al sistema, se le enviará un link de activación a su correo")
    @PostMapping("/registrarse")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody()
    public void registrar(
            @Valid @RequestBody UsuarioCreate usuarioCreate,
            BindingResult bindingResult) {
        Validator.checkValidBody(bindingResult);
        authService.registrar(usuarioCreate);
    }

    @Operation(summary = "Confirma el usuario y podrá hacer login", description = "Esto lo usará el link que se enviará al usuario en su correo")
    @GetMapping("/confirmar")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody()
    public void confirmToken(
            @RequestParam(name = "token", required = true) String token) {
        authService.confirmToken(token);
    }
}

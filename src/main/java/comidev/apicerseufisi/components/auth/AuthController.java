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
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auths")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody()
    public Tokens login(
            @Valid @RequestBody UsuarioLogin usuario,
            BindingResult bindingResult) {
        Validator.checkValidBody(bindingResult);
        return authService.login(usuario);
    }

    @PostMapping("/registrarse")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody()
    public void registrar(
            @Valid @RequestBody UsuarioCreate usuarioCreate,
            BindingResult bindingResult) {
        Validator.checkValidBody(bindingResult);
        authService.registrar(usuarioCreate);
    }

    @GetMapping("/confirmar")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody()
    public void confirmToken(
            @RequestParam(name = "token", required = true) String token) {
        authService.confirmToken(token);
    }
}

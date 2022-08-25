package comidev.apicerseufisi.components.auth;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import comidev.apicerseufisi.components.auth.doc.AuthDoc;
import comidev.apicerseufisi.components.usuario.request.UsuarioCreate;
import comidev.apicerseufisi.components.usuario.request.UsuarioLogin;
import comidev.apicerseufisi.jwt.Tokens;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auths")
@AllArgsConstructor
public class AuthController implements AuthDoc {
    private final AuthService authService;

    @PostMapping("/login")
    @ResponseBody
    public Tokens login(@Valid @RequestBody UsuarioLogin body) {
        return authService.login(body);
    }

    @PostMapping("/registrarse")
    @ResponseStatus(HttpStatus.CREATED)
    public void registrar(@Valid @RequestBody UsuarioCreate body) {
        authService.registrar(body);
    }

    @GetMapping("/confirmar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmToken(@RequestParam(name = "token") String token) {
        authService.confirmToken(token);
    }
}

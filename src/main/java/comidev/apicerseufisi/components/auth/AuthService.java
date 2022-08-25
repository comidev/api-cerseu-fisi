package comidev.apicerseufisi.components.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import comidev.apicerseufisi.components.token.TokenService;
import comidev.apicerseufisi.components.usuario.Usuario;
import comidev.apicerseufisi.components.usuario.UsuarioService;
import comidev.apicerseufisi.components.usuario.request.UsuarioCreate;
import comidev.apicerseufisi.components.usuario.request.UsuarioLogin;
import comidev.apicerseufisi.exceptions.HttpException;
import comidev.apicerseufisi.jwt.Tokens;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    private final LoginService loginService;
    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    public Tokens login(UsuarioLogin body) {
        try {
            return loginService.login(body);
        } catch (DisabledException e) {
            Usuario usuarioDB = usuarioService.findByCorreo(body.getCorreo());
            String message = tokenService.resendToken(usuarioDB)
                    ? "Hemos reenviado un enlace a su correo, activar por favor :D"
                    : "Tiene un enlace en su correo por activar :D";
            throw new HttpException(HttpStatus.UNAUTHORIZED, message);
        }
    }

    @Transactional
    public void registrar(UsuarioCreate body) {
        Usuario usuario = usuarioService.registrarUsuario(body);
        tokenService.sendToken(usuario);
    }

    @Transactional
    public void confirmToken(String token) {
        String correo = tokenService.confirmToken(token);
        usuarioService.activarUsuarioByCorreo(correo);
    }
}

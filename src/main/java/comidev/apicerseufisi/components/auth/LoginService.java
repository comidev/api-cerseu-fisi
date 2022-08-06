package comidev.apicerseufisi.components.auth;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import comidev.apicerseufisi.components.usuario.Usuario;
import comidev.apicerseufisi.components.usuario.request.UsuarioLogin;
import comidev.apicerseufisi.exceptions.HttpException;
import comidev.apicerseufisi.jwt.JwtService;
import comidev.apicerseufisi.jwt.Payload;
import comidev.apicerseufisi.jwt.Tokens;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public Tokens login(UsuarioLogin usuario) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usuario.getCorreo(),
                            usuario.getPassword()));
        } catch (BadCredentialsException e) {
            String message = "Username y/o password incorrecto(s)";
            throw new HttpException(HttpStatus.UNAUTHORIZED, message);
        }

        Usuario principal = (Usuario) authentication.getPrincipal();

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        // * Devolvemos token :D
        return jwtService.createTokens(new Payload(principal.getId(),
                principal.getCorreo(), List.of(principal.getRol().toString())));
    }
}

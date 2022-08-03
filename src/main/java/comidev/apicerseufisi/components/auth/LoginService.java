package comidev.apicerseufisi.components.auth;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
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

        Object credentials = authentication.getCredentials();
        Object details = authentication.getDetails();
        Object principal = authentication.getPrincipal();
        String name = authentication.getName();
        String authorities = authentication.getAuthorities().toString();
        String authorities2 = authentication.getAuthorities().stream().toString();
        String authorities3 = authentication.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.joining(" <- :v -> "));

        System.out.println("\n\nCredentials -> " + credentials
                + "\nDetails -> " + details
                + "\nPrincipal -> " + principal
                + "\nName -> " + name
                + "\nAuth1,2,3 -> " + authorities + ", "
                + authorities2 + ", " + authorities3);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        // * Devolvemos token :D
        return jwtService.createTokens(new Payload(
                0l,
                usuario.getCorreo(),
                List.of("ROLE_USER")));
    }
}

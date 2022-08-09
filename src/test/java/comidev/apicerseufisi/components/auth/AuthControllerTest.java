package comidev.apicerseufisi.components.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import comidev.apicerseufisi.components.usuario.Usuario;
import comidev.apicerseufisi.components.usuario.request.UsuarioCreate;
import comidev.apicerseufisi.components.usuario.request.UsuarioLogin;
import comidev.apicerseufisi.components.usuario.utils.Rol;
import comidev.apicerseufisi.components.usuario.utils.Sexo;
import comidev.apicerseufisi.config.ApiIntegrationTest;
import comidev.apicerseufisi.services.Fabric;
import comidev.apicerseufisi.services.Request;
import comidev.apicerseufisi.services.Response;

// * PATH: /auths
@ApiIntegrationTest
public class AuthControllerTest {
    @Autowired
    private Fabric fabric;
    @Autowired
    private Request request;

    // * POST -> /login
    @Test
    void OK_CuandoSeLogeaCorrectamente_login() throws Exception {
        // Arreglar
        String password = "123456";
        Usuario usuario = fabric.createUsuario(null, password);
        UsuarioLogin body = new UsuarioLogin(usuario.getCorreo(), password);

        // Actuar
        Response res = request.post("/auths/login")
                .body(body)
                .send();

        // Afirmar
        assertEquals(HttpStatus.OK, res.status());
        assertTrue(res.bodyString().contains("accessToken"));
    }

    @Test
    void UNAUTHORIZED_CuandoLasCredencialesSonIncorrectas_login()
            throws Exception {
        // Arreglar
        String password = "123456";
        Usuario usuario = fabric.createUsuario(null, password);
        UsuarioLogin body = new UsuarioLogin(usuario.getCorreo(), "password");

        // Actuar
        Response res = request.post("/auths/login")
                .body(body)
                .send();

        // Afirmar
        assertEquals(HttpStatus.UNAUTHORIZED, res.status());
        assertTrue(res.bodyString().contains("Username y/o password incorrecto(s)"));
    }

    @Test
    void CONFLICT_CuandoAlgunDatoSeRepite_registrarse() throws Exception {
        // Arreglar
        Usuario usuario = fabric.createUsuario(null, null);
        UsuarioCreate body = new UsuarioCreate(
                usuario.getCodigo(),
                "nombre", "apellido",
                usuario.getDni(),
                usuario.getTelefono(),
                Sexo.MASCULINO,
                usuario.getCorreo(),
                "password", Rol.ALUMNO);

        // Actuar
        Response res = request.post("/auths/registrarse")
                .body(body)
                .send();

        // Afirmar
        assertEquals(HttpStatus.CONFLICT, res.status());
    }

    // * GET -> /confirmar
    @Test
    void OK_CuandoConfirmaYActivaAlUsuario_confirmToken() throws Exception {
        // Arreglar
        String token = fabric.uuid();
        fabric.createToken(token, null);

        // Actuar
        Response res = request.get("/auths/confirmar")
                .addParam("token", token)
                .send();

        // Afirmar
        assertEquals(HttpStatus.OK, res.status());
    }

    @Test
    void BAD_REQUEST_CuandoConfirmaYActivaAlUsuario_confirmToken()
            throws Exception {
        // Arreglar
        String token = fabric.uuid();
        fabric.createToken(token, null);

        // Actuar
        Response res = request.get("/auths/confirmar")
                .send();

        // Afirmar
        assertEquals(HttpStatus.BAD_REQUEST, res.status());
    }
}

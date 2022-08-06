package comidev.apicerseufisi.components.usuario;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import comidev.apicerseufisi.components.alumno.Alumno;
import comidev.apicerseufisi.components.usuario.request.UsuarioUpdate;
import comidev.apicerseufisi.components.usuario.response.UsuarioDetails;
import comidev.apicerseufisi.components.usuario.utils.Rol;
import comidev.apicerseufisi.components.usuario.utils.Sexo;
import comidev.apicerseufisi.config.ApiIntegrationTest;
import comidev.apicerseufisi.services.Fabric;
import comidev.apicerseufisi.services.Request;
import comidev.apicerseufisi.services.Response;

import static org.junit.jupiter.api.Assertions.*;

@ApiIntegrationTest
public class UsuarioControllerTest {
    @Autowired
    private Request request;
    @Autowired
    private Fabric fabric;

    // ! Cuando se prueba con ALUMNO, también
    // ! podría ser con DOCENTE :D
    // * GET -> /usuarios
    @Test
    void OK_CuandoDevuelveLosUsuarios_GetAll() throws Exception {
        // Arreglar
        Usuario usuario = fabric.createUsuario(null, null);

        // Actuar
        Response res = request.get("/usuarios")
                .send();

        // Afirmar
        res.isStatus(HttpStatus.OK);
        assertTrue(res.bodyString().contains(usuario.getCodigo()));
    }

    @Test
    void OK_CuandoDevuelveLosAlumnosValido_GetAll() throws Exception {
        // Arreglar
        Alumno alumno = fabric.createAlumno(null);

        // Actuar
        Response res = request.get("/usuarios")
                .addParam("rol", Rol.ALUMNO)
                .send();

        // Afirmar
        res.isStatus(HttpStatus.OK);
        assertTrue(res.bodyString().contains(alumno.getUsuario().getCodigo()));
    }

    @Test
    void BAD_REQUEST_CuandoIntroducenUnRolInexistente_GetAll() throws Exception {
        // Arreglar

        // Actuar
        Response res = request.get("/usuarios")
                .addParam("rol", "DIOS")
                .send();

        // Afirmar
        res.isStatus(HttpStatus.BAD_REQUEST);
    }

    // * GET -> /usuarios/{id}
    @Test
    void OK_CuandoDevuelveElUsuario_GetById() throws Exception {
        // Arreglar
        Usuario usuario = fabric.createUsuario(null, null);

        // Actuar
        Response res = request.get("/usuarios/" + usuario.getId())
                .send();

        // Afirmar
        res.isStatus(HttpStatus.OK);
        UsuarioDetails body = res.body(UsuarioDetails.class);
        assertEquals(body.getCodigo(), usuario.getCodigo());
    }

    @Test
    void OK_CuandoDevuelveElAlumno_GetById() throws Exception {
        // Arreglar
        Alumno alumno = fabric.createAlumno(null);

        // Actuar
        Response res = request.get("/usuarios/" + alumno.getId())
                .addParam("rol", Rol.ALUMNO)
                .send();

        // Afirmar
        res.isStatus(HttpStatus.OK);
        UsuarioDetails body = res.body(UsuarioDetails.class);
        assertEquals(body.getCodigo(), alumno.getUsuario().getCodigo());
    }

    @Test
    void BAD_REQUEST_CuandoIntroducenUnRolInvalido_GetById() throws Exception {
        // Arreglar

        // Actuar
        Response res = request.get("/usuarios/" + 666)
                .addParam("rol", Rol.COORDINADOR)
                .send();

        // Afirmar
        res.isStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    void NOT_FOUND_CuandoNoExiste_GetById() throws Exception {
        // Arreglar

        // Actuar
        Response res = request.get("/usuarios/" + 666)
                .addParam("rol", Rol.ALUMNO)
                .send();

        // Afirmar
        res.isStatus(HttpStatus.NOT_FOUND);
    }

    // * PUT -> /usuarios/{id}
    private UsuarioUpdate createUsuarioUpdate(String actualPassword) {
        return new UsuarioUpdate(fabric.uuid(),
                "null", "null",
                fabric.uuid().substring(0, 8),
                fabric.uuid().substring(0, 9),
                Sexo.MASCULINO, fabric.uuid() + "@gmail.com", "nuevoPassword", actualPassword);
    }

    @Test
    void OK_CuandoActualizaCorrectamente_ActualizarUsuario() throws Exception {
        // Arreglar
        String password = "123456";
        Usuario usuario = fabric.createUsuario(null, password);
        UsuarioUpdate content = createUsuarioUpdate(password);

        // Actuar
        Response res = request.put("/usuarios/" + usuario.getId())
                .body(content)
                .send();

        // Afirmar
        res.isStatus(HttpStatus.OK);
    }

    // * DELETE -> /usuarios/{id}
    @Test
    void OK_CuandoEliminaCorrectamente_EliminarUsuario() throws Exception {
        // Arreglar
        String password = "123456";
        Usuario usuario = fabric.createUsuario(null, password);

        // Actuar
        Response res = request.delete("/usuarios/" + usuario.getId())
                .addParam("password", password)
                .send();

        // Afirmar
        res.isStatus(HttpStatus.OK);
    }
}

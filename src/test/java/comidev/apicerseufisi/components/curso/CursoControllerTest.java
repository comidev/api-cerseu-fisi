package comidev.apicerseufisi.components.curso;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import comidev.apicerseufisi.components.curso.request.CursoCreate;
import comidev.apicerseufisi.components.curso.request.CursoUpdate;
import comidev.apicerseufisi.config.ApiIntegrationTest;
import comidev.apicerseufisi.services.Fabric;
import comidev.apicerseufisi.services.Request;
import comidev.apicerseufisi.services.Response;

// * PATH: /cursos
@ApiIntegrationTest
public class CursoControllerTest {
    @Autowired
    private Fabric fabric;
    @Autowired
    private Request request;

    // * GET ->
    @Test
    void OK_CuandoDevuelveLosCursos_getAll() throws Exception {
        // Arreglar
        String codigo = fabric.createCurso(null, null).getCodigo();

        // Actuar
        Response response = request.get("/cursos").send();

        // Afirmar
        response.isStatus(HttpStatus.OK);
        assertTrue(response.bodyString().contains(codigo));
    }

    // * GET -> /{id}
    @Test
    void OK_CuandoDevuelveLosCursos_getById() throws Exception {
        // Arreglar
        Curso curso = fabric.createCurso(null, null);
        String codigo = curso.getCodigo();

        // Actuar
        Response response = request.get("/cursos/" + curso.getId())
                .send();

        // Afirmar
        response.isStatus(HttpStatus.OK);
        assertTrue(response.bodyString().contains(codigo));
    }

    @Test
    void NOT_FOUND_CuandoDevuelveLosCursos_getById() throws Exception {
        // Arreglar

        // Actuar
        Response response = request.get("/cursos/" + 40822)
                .send();

        // Afirmar
        response.isStatus(HttpStatus.NOT_FOUND);
    }

    // * POST ->
    @Test
    void OK_CuandoGuardaElCurso_saveCurso() throws Exception {
        // Arreglar
        CursoCreate body = new CursoCreate("nombre", fabric.uuid(),
                7, 2018, 4);

        // Actuar
        Response response = request.post("/cursos")
                .body(body)
                .send();

        // Afirmar
        response.isStatus(HttpStatus.CREATED);
    }

    @Test
    void CONFLICT_CuandoElCodigoYaExiste_saveCurso() throws Exception {
        // Arreglar
        String codigo = fabric.uuid();
        fabric.createCurso(codigo, null);
        CursoCreate body = new CursoCreate("nombre", codigo,
                7, 2018, 4);

        // Actuar
        Response response = request.post("/cursos")
                .body(body)
                .send();
        // Afirmar
        response.isStatus(HttpStatus.CONFLICT);
    }

    // * PUT -> /{id}
    @Test
    void OK_CuandoActualizaElCurso_updateCurso() throws Exception {
        // Arreglar
        Curso curso = fabric.createCurso(null, null);
        CursoUpdate body = new CursoUpdate("nombre", fabric.uuid(),
                7, 2018, 4);

        // Actuar
        Response response = request.put("/cursos/" + curso.getId())
                .body(body)
                .send();

        // Afirmar
        response.isStatus(HttpStatus.OK);
    }

    @Test
    void NOT_FOUND_CuandoNoEncuentraElCurso_updateCurso() throws Exception {
        // Arreglar
        CursoUpdate body = new CursoUpdate("nombre", fabric.uuid(),
                7, 2018, 4);

        // Actuar
        Response response = request.put("/cursos/" + 666)
                .body(body)
                .send();

        // Afirmar
        response.isStatus(HttpStatus.NOT_FOUND);
    }

    // * DELETE -> /{id}
    @Test
    void OK_CuandoEliminaElCurso_eliminarCurso() throws Exception {
        // Arreglar
        Curso curso = fabric.createCurso(null, null);

        // Actuar
        Response response = request.delete("/cursos/" + curso.getId())
                .send();

        // Afirmar
        response.isStatus(HttpStatus.OK);
    }
}

package comidev.apicerseufisi.components.aula;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import comidev.apicerseufisi.components.aula.request.AulaCreate;
import comidev.apicerseufisi.components.aula.request.AulaUpdate;
import comidev.apicerseufisi.config.ApiIntegrationTest;
import comidev.apicerseufisi.services.Fabric;
import comidev.apicerseufisi.services.Request;
import comidev.apicerseufisi.services.Response;

// * PATH: /aulas
@ApiIntegrationTest
public class AulaControllerTest {
    @Autowired
    private Fabric fabric;
    @Autowired
    private Request request;

    // * GET ->
    @Test
    void OK_CuandoDevuelveLasAulas_getAll() throws Exception {
        // Arreglar
        fabric.createAula();

        // Actuar
        Response response = request.get("/aulas").send();

        // Afirmar
        response.isStatus(HttpStatus.OK);
    }

    // * GET -> /{id}
    @Test
    void OK_CuandoDevuelveElAula_getById() throws Exception {
        // Arreglar
        Aula aula = fabric.createAula();

        // Actuar
        Response response = request.get("/aulas/" + aula.getId())
                .send();

        // Afirmar
        response.isStatus(HttpStatus.OK);
        assertTrue(response.bodyString()
                .contains(String.valueOf(aula.getId())));
    }

    // * POST ->
    @Test
    void CREATED_CuandoGuardaElAula_saveAula() throws Exception {
        // Arreglar
        AulaCreate body = new AulaCreate(50);

        // Actuar
        Response response = request.post("/aulas")
                .body(body)
                .send();

        // Afirmar
        response.isStatus(HttpStatus.CREATED);
        assertTrue(response.bodyString()
                .contains(String.valueOf(body.getCapacidad())));
    }

    // * PUT -> /{id}
    @Test
    void OK_CuandoActualizaElAula_updateAula() throws Exception {
        // Arreglar
        Aula aula = fabric.createAula();
        AulaUpdate body = new AulaUpdate(50);

        // Actuar
        Response response = request.put("/aulas/" + aula.getId())
                .body(body)
                .send();

        // Afirmar
        response.isStatus(HttpStatus.OK);
    }

    // * DELETE -> /{id}
    @Test
    void OK_CuandoEliminaElAula_eliminarAula() throws Exception {
        // Arreglar
        Aula aula = fabric.createAula();

        // Actuar
        Response response = request.delete("/aulas/" + aula.getId())
                .send();

        // Afirmar
        response.isStatus(HttpStatus.OK);
    }
}

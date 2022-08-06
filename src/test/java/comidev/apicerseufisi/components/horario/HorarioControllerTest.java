package comidev.apicerseufisi.components.horario;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import comidev.apicerseufisi.components.aula.Aula;
import comidev.apicerseufisi.components.curso.Curso;
import comidev.apicerseufisi.components.curso.utils.CursoEstado;
import comidev.apicerseufisi.components.docente.Docente;
import comidev.apicerseufisi.components.horario.request.HorarioCreate;
import comidev.apicerseufisi.components.horario.utils.HorarioEstado;
import comidev.apicerseufisi.config.ApiIntegrationTest;
import comidev.apicerseufisi.services.Fabric;
import comidev.apicerseufisi.services.Request;
import comidev.apicerseufisi.services.Response;
import comidev.apicerseufisi.utils.Dia;

// * PATH: /horarios
@ApiIntegrationTest
public class HorarioControllerTest {
    @Autowired
    private Fabric fabric;
    @Autowired
    private Request request;

    // * POST ->
    @Test
    void CREATED_CuandoRegistraElHorario_registrarHorario() throws Exception {
        // Arreglar
        Curso curso = fabric.createCurso(null, null);
        Docente docente = fabric.createDocente(null);
        fabric.createDisponibilidad(docente, curso, fabric.createFecha());
        HorarioCreate body = new HorarioCreate(curso.getId(), docente.getId(),
                "18:00", "22:00", Dia.LUNES);

        // Actuar
        Response response = request.post("/horarios")
                .body(body)
                .send();

        // Afirmar
        response.isStatus(HttpStatus.CREATED);
    }

    // * GET -> ?estado
    @Test
    void OK_CuandoDevuelveLosHorarios_getAll() throws Exception {
        // Arreglar
        Horario horario = fabric.createHorario(null,
                null, null, null, null);

        // Actuar
        Response response = request.get("/horarios")
                .send();

        // Afirmar
        response.isStatus(HttpStatus.OK);
        assertTrue(response.bodyString().contains(horario.getId().toString()));
    }

    // * GET -> /{id}
    @Test
    void OK_CuandoDevuelveElHorario_getById() throws Exception {
        // Arreglar
        Horario horario = fabric.createHorario(null,
                null, null, null, null);

        // Actuar
        Response response = request.get("/horarios/" + horario.getId().toString())
                .send();

        // Afirmar
        response.isStatus(HttpStatus.OK);
        assertTrue(response.bodyString().contains(horario.getId().toString()));
    }

    // * GET -> /aula?inicio{}&fin={}&dia={}
    @Test
    void OK_CuandoDevuelveLasAulas_getAllAulasByHoraAndDia() throws Exception {
        // Arreglar
        Aula aula = fabric.createAula();
        fabric.createHorario(null, null, aula, null, null);

        // Actuar
        Response response = request.get("/horarios/aula")
                .addParam("inicio", "18:00")
                .addParam("fin", "22:00")
                .addParam("dia", Dia.LUNES)
                .send();

        // Afirmar
        response.isStatus(HttpStatus.OK);
        String bodyRes = response.bodyString();
        assertTrue(bodyRes.contains(aula.getId().toString()));
        assertTrue(bodyRes.contains(aula.getCapacidad().toString()));
    }

    // * GET -> /aula/{aulaId}
    @Test
    void OK_CuandoDevuelveLosHorariosPorAula_getAllByAula() throws Exception {
        // Arreglar
        Aula aula = fabric.createAula();
        Horario horario = fabric.createHorario(null, null,
                aula, null, null);

        // Actuar
        Response response = request.get("/horarios/aula/" + aula.getId())
                .send();

        // Afirmar
        response.isStatus(HttpStatus.OK);
        String bodyRes = response.bodyString();
        assertTrue(bodyRes.contains(horario.getId().toString()));
        assertTrue(bodyRes.contains(horario.getDia().toString()));
    }

    // * PATCH -> /reservar?horarioId={}&aulaId={}
    @Test
    void OK_CuandoReservaElAula_reservarAula() throws Exception {
        // Arreglar
        Curso curso = fabric.createCurso(null, CursoEstado.APTO);
        Horario horario = fabric.createHorario(curso, null,
                null, null, null);
        Aula aula = fabric.createAula();

        // Actuar
        Response response = request.patch("/horarios/reservar")
                .addParam("horarioId", horario.getId())
                .addParam("aulaId", aula.getId())
                .send();

        // Afirmar
        response.isStatus(HttpStatus.OK);
    }

    // * PATCH -> /{id}?estado={}
    @Test
    void OK_CuandoEstableceElEstadoDelHorario_setHorarioEstado() throws Exception {
        // Arreglar
        Horario horario = fabric.createHorario(null, null,
                null, null, null);

        // Actuar
        Response response = request.patch("/horarios/" + horario.getId())
                .addParam("estado", HorarioEstado.RECHAZADO)
                .send();

        // Afirmar
        response.isStatus(HttpStatus.OK);
    }

    // * GET -> /docente/{docenteId}
    @Test
    void OK_CuandoDevuelveLosHorariosDelDocente_getHorariosByDocente() throws Exception {
        // Arreglar
        Docente docente = fabric.createDocente(null);
        Horario horario = fabric.createHorario(null, docente,
                null, null, HorarioEstado.ACTIVADO);

        // Actuar
        Response response = request.get("/horarios/docente/" + docente.getId())
                .send();

        // Afirmar
        response.isStatus(HttpStatus.OK);
        String bodyRes = response.bodyString();
        assertTrue(bodyRes.contains(horario.getId().toString()));
    }
}

package comidev.apicerseufisi.components.disponibilidad;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import comidev.apicerseufisi.components.curso.Curso;
import comidev.apicerseufisi.components.disponibilidad.request.DisponibilidadCreate;
import comidev.apicerseufisi.components.disponibilidad.request.DisponibilidadUpdate;
import comidev.apicerseufisi.components.docente.Docente;
import comidev.apicerseufisi.components.fecha.dto.FechaCreate;
import comidev.apicerseufisi.config.ApiIntegrationTest;
import comidev.apicerseufisi.services.Fabric;
import comidev.apicerseufisi.services.Request;
import comidev.apicerseufisi.services.Response;
import comidev.apicerseufisi.utils.Dia;

// * PATH: /disponibilidades
@ApiIntegrationTest
public class DisponibilidadControllerTest {
        @Autowired
        private Fabric fabric;
        @Autowired
        private Request request;

        // * POST ->
        @Test
        void CREATED_CuandoGuardaLaDisponibilidad_saveDisponibilidad() throws Exception {
                // Arreglar
                Docente docente = fabric.createDocente(null);
                Curso curso = fabric.createCurso(null, null);
                List<FechaCreate> fechas = List.of(new FechaCreate(Dia.LUNES,
                                "18:00", "22:00"));
                DisponibilidadCreate body = new DisponibilidadCreate(docente.getId(),
                                curso.getId(), fechas);

                // Actuar
                Response response = request.post("/disponibilidades")
                                .body(body)
                                .send();

                // Afirmar
                assertEquals(HttpStatus.CREATED, response.status());
        }

        // * PUT -> ?cursoId={}&docenteId={}
        @Test
        void OK_CuandoActualizaLaDisponibilidad_updateDisponibilidad() throws Exception {
                // Arreglar
                Disponibilidad disponibilidad = fabric.createDisponibilidad(
                                null, null, null);

                List<FechaCreate> fechas = List.of(new FechaCreate(Dia.MARTES,
                                "18:00", "22:00"));

                DisponibilidadUpdate body = new DisponibilidadUpdate(fechas);

                // Actuar
                Response response = request.put("/disponibilidades")
                                .addParam("docenteId", disponibilidad.getDocente().getId())
                                .addParam("cursoId", disponibilidad.getCurso().getId())
                                .body(body)
                                .send();

                // Afirmar
                assertEquals(HttpStatus.OK, response.status());
        }

        // * DELETE -> ?cursoId={}&docenteId={}
        @Test
        void OK_CuandoEliminaLaDisponibilidad_deleteDisponibilidad() throws Exception {
                // Arreglar
                Disponibilidad disponibilidad = fabric.createDisponibilidad(
                                null, null, null);

                // Actuar
                Response response = request.delete("/disponibilidades")
                                .addParam("docenteId", disponibilidad.getDocente().getId())
                                .addParam("cursoId", disponibilidad.getCurso().getId())
                                .send();

                // Afirmar
                assertEquals(HttpStatus.OK, response.status());
        }

        // * GET -> ?cursoId={}&docenteId={}
        @Test
        void OK_CuandoDevuelveLaDisponibilidad_getByDocenteAndCurso() throws Exception {
                // Arreglar
                Disponibilidad disponibilidad = fabric.createDisponibilidad(
                                null, null, null);

                // Actuar
                Response response = request.get("/disponibilidades")
                                .addParam("docenteId", disponibilidad.getDocente().getId())
                                .addParam("cursoId", disponibilidad.getCurso().getId())
                                .send();

                // Afirmar
                assertEquals(HttpStatus.OK, response.status());
                assertTrue(response.bodyString().contains(disponibilidad.getId().toString()));
        }

        // * GET -> /docente/{docenteId}
        @Test
        void OK_CuandoDevuelveLasDisponibilidadesDeUnDocente_getByDocente()
                        throws Exception {
                // Arreglar
                Disponibilidad disponibilidad = fabric.createDisponibilidad(
                                null, null, null);

                // Actuar
                Response response = request.get("/disponibilidades/docente/"
                                + disponibilidad.getDocente().getId())
                                .send();

                // Afirmar
                assertEquals(HttpStatus.OK, response.status());
                assertTrue(response.bodyString().contains(disponibilidad.getId().toString()));
        }
}

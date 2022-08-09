package comidev.apicerseufisi.components.pago;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import comidev.apicerseufisi.components.alumno.Alumno;
import comidev.apicerseufisi.components.curso.Curso;
import comidev.apicerseufisi.components.curso.utils.CursoEstado;
import comidev.apicerseufisi.components.horario.Horario;
import comidev.apicerseufisi.components.horario.utils.HorarioEstado;
import comidev.apicerseufisi.components.pago.request.PagoCreate;
import comidev.apicerseufisi.components.pago.request.PagosCreate;
import comidev.apicerseufisi.components.pago.utils.PagoEstado;
import comidev.apicerseufisi.components.solicitud.Solicitud;
import comidev.apicerseufisi.components.solicitud.request.SolicitudCreate;
import comidev.apicerseufisi.config.ApiIntegrationTest;
import comidev.apicerseufisi.services.Fabric;
import comidev.apicerseufisi.services.Request;
import comidev.apicerseufisi.services.Response;

// * /pagos
@ApiIntegrationTest
public class PagoControllerTest {
        @Autowired
        private Request request;
        @Autowired
        private Fabric fabric;

        // * POST ->
        @Test
        void CREATED_CuandoRegristaLosPagos_registrarPagos() throws Exception {
                // Arreglar
                Alumno alumno = fabric.createAlumno(null);
                String codigo = fabric.uuid();
                fabric.createCurso(codigo, null);
                SolicitudCreate solicitud = new SolicitudCreate("codigo", alumno.getId());
                PagoCreate pago = new PagoCreate(80f, codigo);
                PagosCreate body = new PagosCreate(solicitud, List.of(pago));

                // Actuar
                Response response = request.post("/pagos")
                                .body(body)
                                .send();

                // Afirmar
                assertEquals(HttpStatus.CREATED, response.status());
        }

        // * GET ->
        @Test
        void OK_CuandoDevuelveLosPagosPorSolicitud_getBySolicitud() throws Exception {
                // Arreglar
                Solicitud solicitud = fabric.createSolicitud(null, null);
                Pago pago = fabric.createPago(null, solicitud, null, null);

                // Actuar
                Response response = request.get("/pagos/solicitud/" + solicitud.getId())
                                .send();

                // Afirmar
                assertEquals(HttpStatus.OK, response.status());
                assertTrue(response.bodyContains(pago.getId(), pago.getCursoCodigo(),
                                pago.getMonto(), pago.getPagoEstado()));
        }

        // * GET ->
        @Test
        void OK_CuandoDevuelveLosCursosQueEstanPidiendo_getCursosPedidos()
                        throws Exception {
                // Arreglar
                Curso curso = fabric.createCurso(null, null);
                Curso curso1 = fabric.createCurso(null, null);
                fabric.createCurso(null, null);
                fabric.createPago(curso, null, null, null);
                fabric.createPago(curso1, null, null, null);

                // Actuar
                Response response = request.get("/pagos/cursos-pedidos")
                                .send();

                // Afirmar
                assertEquals(HttpStatus.OK, response.status());
                System.out.println("\n" + response.bodyString());
        }

        // * GET ->
        @Test
        void OK_CuandoLosMatriculadosPorCurso_getMatriculadosByCurso() throws Exception {
                // Arreglar
                Curso curso = fabric.createCurso(null, null);
                fabric.createPago(curso, null, null, null);
                fabric.createPago(curso, null, null, null);
                fabric.createPago(curso, null, null, null);
                fabric.createPago(curso, null, null, null);
                fabric.createPago(curso, null, null, null);
                fabric.createPago(curso, null, null, null);

                // Actuar
                Response response = request.get("/pagos/matriculados")
                                .addParam("curso_codigo", curso.getCodigo())
                                .send();

                // Afirmar
                assertEquals(HttpStatus.OK, response.status());
        }

        // * GET ->
        @Test
        void OK_CuandoDevuelveLosHorariosDeUnAlumno_getHorariosByAlumno() throws Exception {
                // Arreglar
                Curso curso = fabric.createCurso(null, null);
                Horario horario = fabric.createHorario(curso, null, null,
                                null, null);

                Alumno alumno = fabric.createAlumno(null);
                Solicitud solicitud = fabric.createSolicitud(alumno, null);
                fabric.createPago(curso, solicitud, horario, PagoEstado.ACTIVADO);

                // Actuar
                Response response = request.get("/pagos/horarios/" + alumno.getId())
                                .send();

                // Afirmar
                assertEquals(HttpStatus.OK, response.status());
                System.out.println("\n" + response.bodyString());
        }

        // * GET ->
        @Test
        void OK_CuandoXYZ_iniciarMatriculacion() throws Exception {
                // Arreglar
                Alumno alumno = fabric.createAlumno(null);
                Curso curso = fabric.createCurso(null, CursoEstado.APTO);
                Solicitud solicitud = fabric.createSolicitud(alumno, null);
                Horario horario = fabric.createHorario(curso, null, null,
                                null, HorarioEstado.VALIDADO);
                fabric.createPago(curso, solicitud, horario, null);

                // Actuar
                Response response = request.get("/pagos/iniciar-matriculacion")
                                .send();

                // Afirmar
                assertEquals(HttpStatus.OK, response.status());
        }

        // * GET ->
        @Test
        void OK_CuandoXYZ_terminarMatricula() throws Exception {
                // Arreglar

                // Actuar
                Response response = request.get("/pagos/terminar-matricula")
                                .send();

                // Afirmar
                assertEquals(HttpStatus.OK, response.status());
        }
}

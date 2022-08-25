package comidev.apicerseufisi.components.pago.doc;

import java.util.List;

import comidev.apicerseufisi.components.curso.response.CursoDetails;
import comidev.apicerseufisi.components.horario.response.HorarioByAlumno;
import comidev.apicerseufisi.components.pago.request.PagosCreate;
import comidev.apicerseufisi.components.pago.response.Matriculados;
import comidev.apicerseufisi.components.pago.response.PagoBySolicitud;
import io.swagger.v3.oas.annotations.Operation;

public interface PagoDoc {
    @Operation(summary = """
            Registras los pagos del alumno
            """, description = """
            Este debe mandar la solicitud que contiene los pagos.
            """)
    void registrarPagos(PagosCreate body);

    @Operation(summary = """
            Devuelve los pagos por solicitud id
            """, description = """
            Cuando el trabajador quiera ver los detalles de una solicitud puede
            obtener los pagos de esa solicitud :D
            """)
    List<PagoBySolicitud> getPagosBySolicitud(Long solicitudId);

    @Operation(summary = """
            Devuelve los cursos que está siendo pedido por al menos un alumno
            """, description = """
            Cuando quieras ver los cursos que son pedidos.
            """)
    List<CursoDetails> getCursosPedidos();

    @Operation(summary = """
            Devuelve los alumnos matriculados por curso esto es el CUS de cantidad
            de alumnos matriculados :D
            """, description = """
            Cuando accedes al detalle de la lista de curso que son pedidos puedes
            mostrar esto
            """)
    Matriculados getMatriculadosByCurso(String cursoCodigo);

    @Operation(summary = """
            Devuelve los horarios de un alumno por su id
            """, description = """
            Podrías mostrar esto como lo hace el SUM
            """)
    List<HorarioByAlumno> getHorariosByAlumno(Long alumnoId);

    @Operation(summary = """
            Inicias la matricula del sistema
            """, description = """
            Al hacer esta petición inciarás la matricula del sistema
            """)
    void iniciarMatriculacion();

    @Operation(summary = """
            Terminas la matricula del sistema
            """, description = """
            Al hacer esta petición terminas la matricula del sistema
            """)
    void terminarMatricula();
}

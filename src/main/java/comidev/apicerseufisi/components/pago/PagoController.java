package comidev.apicerseufisi.components.pago;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import comidev.apicerseufisi.components.curso.response.CursoDetails;
import comidev.apicerseufisi.components.horario.response.HorarioByAlumno;
import comidev.apicerseufisi.components.pago.request.PagosCreate;
import comidev.apicerseufisi.components.pago.response.Matriculados;
import comidev.apicerseufisi.components.pago.response.PagoBySolicitud;
import comidev.apicerseufisi.exceptions.Validator;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/pagos")
@AllArgsConstructor
public class PagoController {
    private final PagoService pagoService;

    @Operation(summary = "Registras los pagos del alumno", description = "Este debe mandar la solicitud que contiene los pagos.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void registrarPagos(@Valid @RequestBody PagosCreate pagos,
            BindingResult bindingResult) {
        Validator.checkValidBody(bindingResult);
        pagoService.registrarPagos(pagos);
    }

    @Operation(summary = "Devuelve los pagos por solicitud id", description = "Cuando el trabajador quiera ver los datelles de una solicitud puede obtener los pagos de esa solicitud :D")
    @GetMapping("/solicitud/{solicitudId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<PagoBySolicitud> getBySolicitud(@PathVariable Long solicitudId) {
        return pagoService.getBySolicitud(solicitudId);
    }

    @Operation(summary = "Devuelve los cursos que está siendo pedido por al menos un alumno", description = "Cuando quieras ver los cursos que son pedidos.")
    @GetMapping("/cursos-pedidos")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CursoDetails> getCursosPedidos() {
        return pagoService.getCursosPedidos();
    }

    @Operation(summary = "Devuelve los alumnos matriculados por curso esto es el CUS de cantidad de alumnos matriculados :D", description = "Cuando accedes al detalle de la lista de curso que son pedidos puedes mostrar esto")
    @GetMapping("/matriculados")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Matriculados getMatriculadosByCurso(
            @RequestParam(name = "curso_codigo") String cursoCodigo) {
        return pagoService.getMatriculadosByCurso(cursoCodigo);
    }

    @Operation(summary = "Devuelve los horarios de un alumno por su id", description = "Podrías mostrar esto como lo hace el SUM")
    @GetMapping("/horarios/{alumnoId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<HorarioByAlumno> getHorariosByAlumno(@PathVariable Long alumnoId) {
        return pagoService.getHorariosByAlumno(alumnoId);
    }

    @Operation(summary = "Inicias la matricula del sistema", description = "Al hacer esta petición inciarás la matricula del sistema")
    @GetMapping("/iniciar-matriculacion")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void iniciarMatriculacion() {
        pagoService.iniciarMatriculacion();
    }

    @Operation(summary = "Terminas la matricula del sistema", description = "Al hacer esta petición terminas la matricula del sistema")
    @GetMapping("/terminar-matricula")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void terminarMatricula() {
        pagoService.terminarMatricula();
    }

}

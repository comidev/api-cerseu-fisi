package comidev.apicerseufisi.components.pago;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
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
import comidev.apicerseufisi.components.pago.doc.PagoDoc;
import comidev.apicerseufisi.components.pago.request.PagosCreate;
import comidev.apicerseufisi.components.pago.response.Matriculados;
import comidev.apicerseufisi.components.pago.response.PagoBySolicitud;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/pagos")
@AllArgsConstructor
public class PagoController implements PagoDoc {
    private final PagoService pagoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registrarPagos(@Valid @RequestBody PagosCreate body) {
        pagoService.registrarPagos(body);
    }

    @GetMapping("/solicitud/{solicitudId}")
    @ResponseBody
    public List<PagoBySolicitud> getPagosBySolicitud(@PathVariable Long solicitudId) {
        return pagoService.getPagosBySolicitud(solicitudId);
    }

    @GetMapping("/cursos-pedidos")
    @ResponseBody
    public List<CursoDetails> getCursosPedidos() {
        return pagoService.getCursosPedidos();
    }

    @GetMapping("/matriculados")
    @ResponseBody
    public Matriculados getMatriculadosByCurso(
            @RequestParam(name = "curso_codigo") String cursoCodigo) {
        return pagoService.getMatriculadosByCurso(cursoCodigo);
    }

    @GetMapping("/horarios/{alumnoId}")
    @ResponseBody
    public List<HorarioByAlumno> getHorariosByAlumno(@PathVariable Long alumnoId) {
        return pagoService.getHorariosByAlumno(alumnoId);
    }

    @GetMapping("/iniciar-matriculacion")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void iniciarMatriculacion() {
        pagoService.iniciarMatriculacion();
    }

    @GetMapping("/terminar-matricula")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void terminarMatricula() {
        pagoService.terminarMatricula();
    }

}

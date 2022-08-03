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
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/pagos")
@AllArgsConstructor
public class PagoController {
    private final PagoService pagoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void registrarPagos(@Valid @RequestBody PagosCreate pagos,
            BindingResult bindingResult) {
        Validator.checkValidBody(bindingResult);
        pagoService.registrarPagos(pagos);
    }

    @GetMapping("/solicitud/{solicitudId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<PagoBySolicitud> getBySolicitud(@PathVariable Long solicitudId) {
        return pagoService.getBySolicitud(solicitudId);
    }

    @GetMapping("/cursos-pedidos")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CursoDetails> getCursosPedidos() {
        return pagoService.getCursosPedidos();
    }

    @GetMapping("/matriculados")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Matriculados getMatriculadosByCurso(
            @RequestParam(name = "curso_codigo") String cursoCodigo) {
        return pagoService.getMatriculadosByCurso(cursoCodigo);
    }

    @GetMapping("/horarios/{alumnoId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<HorarioByAlumno> getHorariosByAlumno(@PathVariable Long alumnoId) {
        return pagoService.getHorariosByAlumno(alumnoId);
    }

    @GetMapping("/iniciar-matriculacion")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void iniciarMatriculacion() {
        pagoService.iniciarMatriculacion();
    }

    @GetMapping("/teminar-matricula")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void terminarMatricula() {
        pagoService.terminarMatricula();
    }

}

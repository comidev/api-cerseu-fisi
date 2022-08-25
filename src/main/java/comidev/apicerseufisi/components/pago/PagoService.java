package comidev.apicerseufisi.components.pago;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import comidev.apicerseufisi.components.alumno.Alumno;
import comidev.apicerseufisi.components.curso.Curso;
import comidev.apicerseufisi.components.curso.CursoService;
import comidev.apicerseufisi.components.curso.response.CursoDetails;
import comidev.apicerseufisi.components.curso.utils.CursoEstado;
import comidev.apicerseufisi.components.horario.HorarioService;
import comidev.apicerseufisi.components.horario.response.HorarioByAlumno;
import comidev.apicerseufisi.components.pago.request.PagosCreate;
import comidev.apicerseufisi.components.pago.response.Matriculados;
import comidev.apicerseufisi.components.pago.response.PagoBySolicitud;
import comidev.apicerseufisi.components.pago.utils.PagoEstado;
import comidev.apicerseufisi.components.solicitud.Solicitud;
import comidev.apicerseufisi.components.solicitud.SolicitudService;
import comidev.apicerseufisi.exceptions.HttpException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PagoService {
    private final PagoRepo pagoRepo;
    private final HorarioService horarioService;
    private final SolicitudService solicitudService;
    private final CursoService cursoService;

    // ! CUS 05: Registrar constancia de pagos
    @Transactional
    public void registrarPagos(PagosCreate pagos) {
        // * Guardamos solicitud
        Solicitud solicitudDB = solicitudService.registrarSolicitud(pagos.getSolicitud());
        pagos.getPagos().forEach(item -> {
            // * Validar código del curso y el monto
            String codigo = item.getCursoCodigo();
            Curso curso = findCursoByCodigo(codigo);

            curso.validarMonto(item.getMonto());
            // * Registramos Solicitud
            Pago pagoNEW = new Pago(item, solicitudDB);
            pagoRepo.save(pagoNEW);
            if (!curso.getCursoEstado().equals(CursoEstado.APTO)) {
                int size = findByCursoCodigoAndPagoCREADO(codigo).size();
                cursoService.verificarEstado(curso, size);
            }
        });
    }

    private Curso findCursoByCodigo(String codigo) {
        return pagoRepo.findCursoByCodigo(codigo).orElseThrow(() -> {
            String message = "El curso -> (codigo=" + codigo + ") no existe";
            return new HttpException(HttpStatus.NOT_FOUND, message);
        });
    }

    public List<PagoBySolicitud> getPagosBySolicitud(Long solicitudId) {
        return pagoRepo.findBySolicitud(new Solicitud(solicitudId)).stream()
                .map(PagoBySolicitud::new)
                .collect(Collectors.toList());
    }

    // ! CUS 06: Consultar cantidad de alumnos
    public List<CursoDetails> getCursosPedidos() {
        return pagoRepo.findCursoForPagoEstado(PagoEstado.CREADO).stream()
                .map(CursoDetails::new)
                .collect(Collectors.toList());
    }

    public Matriculados getMatriculadosByCurso(String cursoCodigo) {
        List<Pago> pagos = findByCursoCodigoAndPagoCREADO(cursoCodigo);
        if (pagos.isEmpty()) {
            String message = "El curso no tiene matriculados. Ojo: Es curso_codigo no id";
            throw new HttpException(HttpStatus.NOT_ACCEPTABLE, message);
        }
        return new Matriculados(pagos);
    }

    private List<Pago> findByCursoCodigoAndPagoCREADO(String cursoCodigo) {
        return pagoRepo.findByCursoCodigoAndPagoEstado(cursoCodigo, PagoEstado.CREADO);
    }

    public List<HorarioByAlumno> getHorariosByAlumno(Long alumnoId) {
        return pagoRepo.findHorarioByAlumnoAndEstado(
                new Alumno(alumnoId), PagoEstado.ACTIVADO).stream()
                .map(HorarioByAlumno::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void iniciarMatriculacion() {
        // * Todos los pagos que tengan el curso apto los actualizo :D!
        pagoRepo.updateEstadoByEstadoAndCursoEstado(PagoEstado.ACTIVADO,
                PagoEstado.CREADO, CursoEstado.APTO);
        // * Si no alcanzaron el mínimo los devuelvo u-u
        pagoRepo.updateEstadoByEstadoAndCursoEstado(PagoEstado.DEVUELTO,
                PagoEstado.CREADO, CursoEstado.NO_APTO);
        // ? actualizar el horario :v
        horarioService.iniciarMatriculacion();
        cursoService.iniciarMatricula();
    }

    @Transactional
    public void terminarMatricula() {
        // * Los pagos activados los concluimos :D
        pagoRepo.updateEstadoFor(PagoEstado.CONCLUIDO, PagoEstado.ACTIVADO);
        horarioService.terminarMatricula();
        cursoService.terminarMatricula();
    }
}

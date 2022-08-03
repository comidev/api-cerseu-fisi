package comidev.apicerseufisi.components.pago;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import comidev.apicerseufisi.components.curso.Curso;
import comidev.apicerseufisi.components.curso.response.CursoDetails;
import comidev.apicerseufisi.components.curso.utils.CursoCodigo;
import comidev.apicerseufisi.components.curso.utils.CursoEstado;
import comidev.apicerseufisi.components.horario.Horario;
import comidev.apicerseufisi.components.horario.HorarioService;
import comidev.apicerseufisi.components.horario.response.HorarioByAlumno;
import comidev.apicerseufisi.components.pago.request.PagosCreate;
import comidev.apicerseufisi.components.pago.response.Matriculados;
import comidev.apicerseufisi.components.pago.response.PagoBySolicitud;
import comidev.apicerseufisi.components.pago.utils.PagoEstado;
import comidev.apicerseufisi.components.solicitud.Solicitud;
import comidev.apicerseufisi.components.solicitud.SolicitudService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PagoService {
    private final PagoRepo pagoRepo;
    private final HorarioService horarioService;
    private final SolicitudService solicitudService;

    // ! CUS 05: Registrar constancia de pagos
    @Transactional
    public void registrarPagos(PagosCreate pagos) {
        // * Guardamos solicitud
        Solicitud solicitudDB = solicitudService.registrarSolicitud(pagos.getSolicitud());
        pagos.getPagos().forEach((item) -> {
            // * Validar código del curso y el monto
            Curso curso = horarioService.findCursoByCodigo(item.getCursoCodigo());
            curso.validarMonto(item.getMonto());
            // * Registramos Solicitud
            Pago pagoNEW = new Pago(item.getMonto(), solicitudDB);
            pagoRepo.save(pagoNEW);
            if (!curso.getCursoEstado().equals(CursoEstado.APTO)) {
                int size = this.getCantidadMatriculados(item.getCursoCodigo());
                horarioService.verificarCursoEstado(curso, size);
            }
        });
    }

    public List<PagoBySolicitud> getBySolicitud(Long solicitudId) {
        return pagoRepo.findBySolicitud(new Solicitud(solicitudId)).stream()
                .map(PagoBySolicitud::new)
                .toList();
    }

    // ! CUS 06: Consultar cantidad de alumnos
    public List<CursoDetails> getCursosPedidos() {
        List<CursoDetails> cursos = pagoRepo.findByPagoEstado(PagoEstado.CREADO).stream()
                .map(Pago::getCursoCodigo)
                .distinct()
                .map(horarioService::findCursoByCodigo)
                .map(CursoDetails::new)
                .toList();
        return cursos;
    }

    public Matriculados getMatriculadosByCurso(String cursoCodigo) {
        List<Pago> pagos = findByCursoCodigoAndPagoCREADO(cursoCodigo);
        Matriculados matriculados = new Matriculados(pagos);
        return matriculados;
    }

    public int getCantidadMatriculados(String cursoCodigo) {
        return findByCursoCodigoAndPagoCREADO(cursoCodigo).size();
    }

    private List<Pago> findByCursoCodigoAndPagoCREADO(String cursoCodigo) {
        return pagoRepo.findByCursoCodigoAndPagoEstado(cursoCodigo, PagoEstado.CREADO);
    }

    public List<HorarioByAlumno> getHorariosByAlumno(Long alumnoId) {
        return solicitudService.findSolicitudAllByAlumno(alumnoId).stream()
                .map(this::findBySolicitudAndPagoACTIVO)
                .flatMap(List<Pago>::stream)
                .map(Pago::getCursoCodigo)
                .map(horarioService::findHorariosByCodigoCurso)
                .flatMap(List<Horario>::stream)
                .map(HorarioByAlumno::new)
                .toList();
    }

    public List<Pago> findBySolicitudAndPagoACTIVO(Solicitud item) {
        return pagoRepo.findBySolicitudAndPagoEstado(item, PagoEstado.ACTIVADO);
    }

    @Transactional
    public void iniciarMatriculacion() {
        // * Actualizamos el estado de los pagos matriculados :D
        CursoCodigo cursoCodigo = horarioService.iniciarMatriculacion();
        List<Pago> pagosActivados = cursoCodigo.getAperturados().stream()
                .map(this::findByCursoCodigoAndPagoCREADO)
                .flatMap(List<Pago>::stream)
                .map(item -> {
                    item.setPagoEstado(PagoEstado.ACTIVADO);
                    return item;
                })
                .toList();
        List<Pago> pagosDevueltos = cursoCodigo.getNoAperturados().stream()
                .map(this::findByCursoCodigoAndPagoCREADO)
                .flatMap(List<Pago>::stream)
                .map(item -> {
                    item.setPagoEstado(PagoEstado.DEVUELTO);
                    return item;
                })
                .toList();
        pagosActivados.addAll(pagosDevueltos);
        pagoRepo.saveAll(pagosActivados);
    }

    @Transactional
    public void terminarMatricula() {
        List<Pago> pagosConcluidos = pagoRepo
                .findByPagoEstado(PagoEstado.ACTIVADO).stream()
                .map(item -> {
                    item.setPagoEstado(PagoEstado.CONCLUIDO);
                    return item;
                })
                .toList();
        pagoRepo.saveAll(pagosConcluidos);
        horarioService.terminarMatricula();
    }
}

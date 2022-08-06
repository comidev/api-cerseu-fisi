package comidev.apicerseufisi.components.horario;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import comidev.apicerseufisi.components.aula.Aula;
import comidev.apicerseufisi.components.aula.AulaService;
import comidev.apicerseufisi.components.aula.response.AulaDetails;
import comidev.apicerseufisi.components.curso.Curso;
import comidev.apicerseufisi.components.curso.utils.CursoCodigo;
import comidev.apicerseufisi.components.curso.utils.CursoEstado;
import comidev.apicerseufisi.components.disponibilidad.Disponibilidad;
import comidev.apicerseufisi.components.disponibilidad.DisponibilidadService;
import comidev.apicerseufisi.components.docente.Docente;
import comidev.apicerseufisi.components.fecha.Fecha;
import comidev.apicerseufisi.components.horario.request.HorarioCreate;
import comidev.apicerseufisi.components.horario.response.HorarioDetails;
import comidev.apicerseufisi.components.horario.utils.HorarioEstado;
import comidev.apicerseufisi.exceptions.HttpException;
import comidev.apicerseufisi.utils.Dia;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HorarioService {
    private final HorarioRepo horarioRepo;
    private final DisponibilidadService disponibilidadService;
    private final AulaService aulaService;

    // ! CUS - 02: Registrar horarios
    public void registrarHorario(HorarioCreate horarioCreate) {
        Disponibilidad disponibilidadDB = disponibilidadService.findByDocenteAndCurso(horarioCreate.getDocenteId(),
                horarioCreate.getCursoId());
        // * Verificamos si tiene disponibilidad ese día
        Dia dia = horarioCreate.getDia();
        List<Fecha> fechaOpt = disponibilidadDB.getFechas().stream()
                .filter(item -> item.getDia().equals(dia))
                .toList();
        if (fechaOpt.isEmpty()) {
            String message = "No tiene disponibilidad para el dia " + dia.toString();
            throw new HttpException(HttpStatus.BAD_REQUEST, message);
        }
        // * Verificamos si la hora está dentro del intervalo
        Fecha fechaDB = fechaOpt.get(0);
        Horario horarioNEW = new Horario(horarioCreate, disponibilidadDB);
        if (horarioNEW.getInicio().isBefore(fechaDB.getInicio())
                || horarioNEW.getFin().isAfter(fechaDB.getFin())) {
            String message = "No tiene disponibilidad para esas horas";
            throw new HttpException(HttpStatus.BAD_REQUEST, message);
        }
        // * Registramos Horario :D
        horarioRepo.save(horarioNEW);
    }

    public List<HorarioDetails> getAllOrByHorarioEstado(HorarioEstado horarioEstado) {
        if (horarioEstado != null) {
            return horarioRepo.findByHorarioEstado(horarioEstado).stream()
                    .map(HorarioDetails::new)
                    .toList();
        }
        return horarioRepo.findAll().stream()
                .map(HorarioDetails::new)
                .toList();
    }

    public HorarioDetails getById(Long id) {
        return new HorarioDetails(this.findById(id));
    }

    public CursoCodigo iniciarMatriculacion() {
        // * Activamos los válidos y rechazamos los inválidos
        horarioRepo.updateEstadoFor(HorarioEstado.ACTIVADO, HorarioEstado.VALIDADO);
        // * Rechazamos los inválidos
        horarioRepo.updateEstadoFor(HorarioEstado.RECHAZADO, HorarioEstado.OBSERVADO);
        horarioRepo.updateEstadoFor(HorarioEstado.RECHAZADO, HorarioEstado.CREADO);
        return disponibilidadService.getCursoCodigos();
    }

    public void terminarMatricula() {
        horarioRepo.updateEstadoFor(HorarioEstado.ACTIVADO, HorarioEstado.CONCLUIDO);
        disponibilidadService.terminarMatricula();
    }

    // ! CUS 07: Reservación de aulas
    public List<AulaDetails> getAllAulasByHoraAndDia(
            LocalTime inicio, LocalTime fin, Dia dia) {
        List<Long> ids = horarioRepo.findByDiaAndAulaIsNotNull(dia).stream()
                // * Obtenemos los horarios que están ocupados
                .filter(item -> item.getInicio().isBefore(inicio)
                        && item.getFin().isAfter(fin))
                // * Obtenemos las aulas ocupadas en ese horario
                .map(item -> item.getAula().getId())
                .toList();
        if (ids.isEmpty()) {
            return aulaService.getAll();
        }
        // * Filtramos y obtenemos las que NO están ocupadas
        return aulaService.getAll().stream()
                .filter(item -> !ids.contains(item.getId()))
                .toList();
    }

    public List<HorarioDetails> getAllByAula(Long aulaId) {
        return horarioRepo.findByAula(new Aula(aulaId)).stream()
                .map(HorarioDetails::new)
                .toList();
    }

    public void reservarAula(Long horarioId, Long aulaId) {
        // * Validamos y obtenemos: Aula y Horario
        Horario horarioDB = this.findById(horarioId);
        Aula aulaDB = aulaService.findById(aulaId);
        // * Validamos si el curso tiene el minimo
        boolean isApto = horarioDB.getDisponibilidad()
                .getCurso()
                .getCursoEstado()
                .equals(CursoEstado.APTO);
        if (!isApto) {
            String message = "El curso que desea asociar al aula no tiene suficiente (20) matriculados :(";
            throw new HttpException(HttpStatus.BAD_REQUEST, message);
        }
        // * Actualizamos
        horarioDB.setAula(aulaDB);
        horarioRepo.save(horarioDB);
    }

    // ! CUS 03: Registrar autorizacion de Horarios
    public void setHorarioEstado(Long id, HorarioEstado horarioEstado) {
        Horario horarioDB = this.findById(id);
        if (horarioEstado.equals(HorarioEstado.CREADO)) {
            String message = "No se puede volver a 'crearlo'";
            throw new HttpException(HttpStatus.BAD_REQUEST, message);
        }
        if (horarioEstado.equals(HorarioEstado.VALIDADO)) {
            if (horarioDB.getAula() == null) {
                String message = "No tiene aula asignada :v";
                throw new HttpException(HttpStatus.BAD_REQUEST, message);
            }
            boolean isApto = horarioDB.getDisponibilidad()
                    .getCurso()
                    .getCursoEstado()
                    .equals(CursoEstado.APTO);
            if (!isApto) {
                String message = "El curso no es APTO";
                throw new HttpException(HttpStatus.BAD_REQUEST, message);
            }
        }
        horarioDB.setHorarioEstado(horarioEstado);
        horarioRepo.save(horarioDB);
    }

    private Horario findById(Long horarioId) {
        return horarioRepo.findById(horarioId).orElseThrow(() -> {
            String message = "El horario -> (id=" + horarioId + ") no existe!";
            return new HttpException(HttpStatus.NOT_FOUND, message);
        });
    }

    public List<HorarioDetails> getHorariosByDocente(Long docenteId) {
        return findByDocenteForEstadoACTIVADO(docenteId).stream()
                .map(HorarioDetails::new)
                .toList();
    }

    private List<Horario> findByDocenteForEstadoACTIVADO(Long docenteId) {
        return horarioRepo.findByDocenteForEstado(
                new Docente(docenteId), HorarioEstado.ACTIVADO);
    }

    public Curso findCursoByCodigo(String codigo) {
        return disponibilidadService.findCursoByCodigo(codigo);
    }

    public void verificarCursoEstado(Curso curso, int size) {
        disponibilidadService.verificarEstado(curso, size);
    }

    public List<Horario> findHorariosByCodigoCurso(String codigo) {
        return disponibilidadService.findByCursoCodigo(codigo).stream()
                .map(horarioRepo::findByDisponibilidad)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}

package comidev.apicerseufisi.components.disponibilidad;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import comidev.apicerseufisi.components.curso.Curso;
import comidev.apicerseufisi.components.curso.CursoService;
import comidev.apicerseufisi.components.curso.utils.CursoCodigo;
import comidev.apicerseufisi.components.disponibilidad.request.DisponibilidadCreate;
import comidev.apicerseufisi.components.disponibilidad.request.DisponibilidadUpdate;
import comidev.apicerseufisi.components.disponibilidad.response.DisponibilidadDetails;
import comidev.apicerseufisi.components.disponibilidad.response.DisponibilidadListAll;
import comidev.apicerseufisi.components.disponibilidad.response.DisponibilidadListByDocente;
import comidev.apicerseufisi.components.docente.Docente;
import comidev.apicerseufisi.components.docente.DocenteService;
import comidev.apicerseufisi.components.fecha.Fecha;
import comidev.apicerseufisi.exceptions.HttpException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DisponibilidadService {
    private final DisponibilidadRepo disponibilidadRepo;
    private final DocenteService docenteService;
    private final CursoService cursoService;

    public Disponibilidad findByDocenteAndCurso(Long docenteId, Long cursoId) {
        Docente docente = new Docente(docenteId);
        Curso curso = new Curso(cursoId);
        return disponibilidadRepo.findByDocenteAndCurso(docente, curso)
                .orElseThrow(() -> {
                    String message = "El Disponibilidad con (docenteId=" + docenteId
                            + ",cursoId=" + cursoId + ") no existe!";
                    return new HttpException(HttpStatus.NOT_FOUND, message);
                });
    }

    @Transactional
    // ! CUS - 01: Registrar Disponibilidad
    public void saveDisponibilidad(DisponibilidadCreate disponibilidadCreate) {
        // * Validamos Docente y Curso
        Curso cursoDB = cursoService.findById(disponibilidadCreate.getCursoId());
        Docente docenteDB = docenteService.findById(disponibilidadCreate.getDocenteId());
        // * Creamos las Fechas
        List<Fecha> fechasNEW = disponibilidadCreate.getFechas().stream()
                .map(Fecha::new)
                .toList();
        // * Registramos
        disponibilidadRepo.save(new Disponibilidad(docenteDB, cursoDB, fechasNEW));
    }

    public void updateDisponibilidad(Long docenteId, Long cursoId,
            DisponibilidadUpdate update) {
        Disponibilidad disponibilidadDB = this.findByDocenteAndCurso(docenteId, cursoId);
        List<Fecha> fechas = update.getFechas().stream()
                .map(Fecha::new)
                .collect(Collectors.toList());
        disponibilidadDB.setFechas(fechas);
        disponibilidadRepo.save(disponibilidadDB);
    }

    public void deleteDisponibilidad(Long docenteId, Long cursoId) {
        Long id = findByDocenteAndCurso(docenteId, cursoId).getId();
        disponibilidadRepo.deleteById(id);
    }

    public DisponibilidadDetails getByDocenteAndCurso(Long docenteId, Long cursoId) {
        return new DisponibilidadDetails(findByDocenteAndCurso(docenteId, cursoId));
    }

    public List<DisponibilidadListByDocente> getByDocente(Long docenteId) {
        return findByDocente(docenteId).stream()
                .map(DisponibilidadListByDocente::new)
                .toList();
    }

    public List<Disponibilidad> findByDocente(Long docenteId) {
        return disponibilidadRepo.findByDocente(new Docente(docenteId));
    }

    public List<DisponibilidadListAll> getAll() {
        return disponibilidadRepo.findAll().stream()
                .map(DisponibilidadListAll::new).toList();
    }

    public List<Disponibilidad> findByCursoCodigo(String codigo) {
        return disponibilidadRepo.findByCurso(findCursoByCodigo(codigo));
    }

    public Curso findCursoByCodigo(String codigo) {
        return cursoService.findCursoByCodigo(codigo);
    }

    public void verificarEstado(Curso curso, int size) {
        cursoService.verificarEstado(curso, size);
    }

    public CursoCodigo getCursoCodigos() {
        return cursoService.getCursoCodigos();
    }

    public void terminarMatricula() {
        cursoService.terminarMatricula();
    }
}

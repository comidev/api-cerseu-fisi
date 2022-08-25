package comidev.apicerseufisi.components.curso;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import comidev.apicerseufisi.components.curso.request.CursoCreate;
import comidev.apicerseufisi.components.curso.request.CursoUpdate;
import comidev.apicerseufisi.components.curso.response.CursoDetails;
import comidev.apicerseufisi.components.curso.utils.CursoEstado;
import comidev.apicerseufisi.exceptions.HttpException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CursoService {
    public static final Float PRECIO_POR_CREDITO = 20f;
    public static final Integer MIN_ALUMNOS_MATRICULADOS = 20;
    private final CursoRepo cursoRepo;

    public Curso findById(Long id) {
        return cursoRepo.findById(id).orElseThrow(() -> {
            String message = "El curso -> (cursoId=" + id + ") no existe";
            return new HttpException(HttpStatus.NOT_FOUND, message);
        });
    }

    public Curso findCursoByCodigo(String codigo) {
        return cursoRepo.findByCodigo(codigo).orElseThrow(() -> {
            String message = "El curso -> (codigo=" + codigo + ") no existe";
            return new HttpException(HttpStatus.NOT_FOUND, message);
        });
    }

    public void verificarEstado(Curso curso, int size) {
        if (size >= MIN_ALUMNOS_MATRICULADOS) {
            curso.setCursoEstado(CursoEstado.APTO);
            cursoRepo.save(curso);
        }
        if (size == 1) {
            curso.setCursoEstado(CursoEstado.NO_APTO);
            cursoRepo.save(curso);
        }
    }

    public void iniciarMatricula() {
        cursoRepo.updateEstadoFor(CursoEstado.APERTURADO, CursoEstado.APTO);
        cursoRepo.updateEstadoFor(CursoEstado.NO_APERTURADO, CursoEstado.NO_APTO);
    }

    public void terminarMatricula() {
        cursoRepo.updateEstadoFor(CursoEstado.APERTURADO, CursoEstado.NO_APERTURADO);
    }

    public List<CursoDetails> getAllCursos() {
        return cursoRepo.findAll().stream()
                .map(CursoDetails::new)
                .collect(Collectors.toList());
    }

    public CursoDetails getCursoById(Long id) {
        return new CursoDetails(this.findById(id));
    }

    public CursoDetails saveCurso(CursoCreate aulaCreate) {
        Curso cursoNEW = new Curso(aulaCreate);
        return new CursoDetails(cursoRepo.save(cursoNEW));
    }

    public void updateCurso(Long id, CursoUpdate cursoUpdate) {
        Curso cursoDB = this.findById(id);
        cursoDB.update(cursoUpdate);
        cursoRepo.save(cursoDB);
    }

    public void eliminarCurso(Long id) {
        cursoRepo.delete(this.findById(id));
    }
}

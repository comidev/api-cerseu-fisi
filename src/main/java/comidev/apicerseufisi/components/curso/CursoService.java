package comidev.apicerseufisi.components.curso;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import comidev.apicerseufisi.components.curso.request.CursoCreate;
import comidev.apicerseufisi.components.curso.request.CursoUpdate;
import comidev.apicerseufisi.components.curso.response.CursoDetails;
import comidev.apicerseufisi.components.curso.utils.CursoCodigo;
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

    public CursoCodigo getCursoCodigos() {
        List<Curso> aperturados = cursoRepo
                .findByCursoEstado(CursoEstado.APTO).stream()
                .map(item -> {
                    item.setCursoEstado(CursoEstado.APERTURADO);
                    return item;
                })
                .toList();
        List<Curso> noAperturados = cursoRepo
                .findByCursoEstado(CursoEstado.NO_APTO).stream()
                .map(item -> {
                    item.setCursoEstado(CursoEstado.NO_APERTURADO);
                    return item;
                })
                .toList();
        List<String> codigoAperturados = aperturados.stream()
                .map(Curso::getCodigo)
                .toList();
        List<String> codigoNoAperturados = noAperturados.stream()
                .map(Curso::getCodigo)
                .toList();
        aperturados.addAll(noAperturados);
        cursoRepo.saveAll(aperturados);
        return new CursoCodigo(codigoAperturados, codigoNoAperturados);
    }

    public void terminarMatricula() {
        List<Curso> cursos = cursoRepo
                .findByCursoEstado(CursoEstado.APERTURADO).stream()
                .map(item -> {
                    item.setCursoEstado(CursoEstado.NO_APERTURADO);
                    return item;
                })
                .toList();
        cursoRepo.saveAll(cursos);
    }

    // * C - R - U - D

    public List<CursoDetails> getAll() {
        return cursoRepo.findAll().stream()
                .map(CursoDetails::new)
                .toList();
    }

    public CursoDetails getById(Long id) {
        return new CursoDetails(this.findById(id));
    }

    public CursoDetails saveCurso(CursoCreate aulaCreate) {
        Curso cursoNEW = new Curso(aulaCreate);
        return new CursoDetails(cursoRepo.save(cursoNEW));
    }

    public void updateCurso(CursoUpdate cursoUpdate, Long id) {
        Curso cursoDB = this.findById(id);
        cursoDB.update(cursoUpdate);
        cursoRepo.save(cursoDB);
    }

    public void eliminarCurso(Long id) {
        cursoRepo.delete(this.findById(id));
    }
}

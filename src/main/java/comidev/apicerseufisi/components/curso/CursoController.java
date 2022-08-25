package comidev.apicerseufisi.components.curso;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import comidev.apicerseufisi.components.curso.doc.CursoDoc;
import comidev.apicerseufisi.components.curso.request.CursoCreate;
import comidev.apicerseufisi.components.curso.request.CursoUpdate;
import comidev.apicerseufisi.components.curso.response.CursoDetails;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/cursos")
@AllArgsConstructor
public class CursoController implements CursoDoc {
    private final CursoService cursoService;

    @GetMapping
    @ResponseBody
    public List<CursoDetails> getAllCursos() {
        return cursoService.getAllCursos();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public CursoDetails getCursoById(@PathVariable Long id) {
        return cursoService.getCursoById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CursoDetails saveCurso(@Valid @RequestBody CursoCreate body) {
        return cursoService.saveCurso(body);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCurso(@PathVariable Long id,
            @Valid @RequestBody CursoUpdate cursoUpdate) {
        cursoService.updateCurso(id, cursoUpdate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarCurso(@PathVariable Long id) {
        cursoService.eliminarCurso(id);
    }
}

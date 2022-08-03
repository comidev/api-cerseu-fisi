package comidev.apicerseufisi.components.curso;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

import comidev.apicerseufisi.components.curso.request.CursoCreate;
import comidev.apicerseufisi.components.curso.request.CursoUpdate;
import comidev.apicerseufisi.components.curso.response.CursoDetails;
import comidev.apicerseufisi.exceptions.Validator;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/cursos")
@AllArgsConstructor
public class CursoController {
    private final CursoService cursoService;

    // * C - R - U - D
    @GetMapping
    public ResponseEntity<List<CursoDetails>> getAll() {
        List<CursoDetails> body = cursoService.getAll();
        return ResponseEntity.status(body.isEmpty() ? 204 : 200).body(body);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CursoDetails getById(@PathVariable Long id) {
        return cursoService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CursoDetails saveCurso(
            @Valid @RequestBody CursoCreate aulaCreate,
            BindingResult bindingResult) {
        Validator.checkValidBody(bindingResult);
        return cursoService.saveCurso(aulaCreate);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateCurso(
            @Valid @RequestBody CursoUpdate cursoUpdate,
            BindingResult bindingResult, @PathVariable Long id) {
        Validator.checkValidBody(bindingResult);
        cursoService.updateCurso(cursoUpdate, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void eliminarCurso(@PathVariable Long id) {
        cursoService.eliminarCurso(id);
    }
}

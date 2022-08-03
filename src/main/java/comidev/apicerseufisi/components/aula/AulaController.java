package comidev.apicerseufisi.components.aula;

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

import comidev.apicerseufisi.components.aula.request.AulaCreate;
import comidev.apicerseufisi.components.aula.request.AulaUpdate;
import comidev.apicerseufisi.components.aula.response.AulaDetails;
import comidev.apicerseufisi.exceptions.Validator;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/aulas")
@AllArgsConstructor
public class AulaController {
    private final AulaService aulaService;

    @GetMapping
    public ResponseEntity<List<AulaDetails>> getAll() {
        List<AulaDetails> body = aulaService.getAll();
        return ResponseEntity.status(body.isEmpty() ? 204 : 200).body(body);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AulaDetails getById(@PathVariable Long id) {
        return aulaService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public AulaDetails saveAula(
            @Valid @RequestBody AulaCreate aulaCreate,
            BindingResult bindingResult) {
        Validator.checkValidBody(bindingResult);
        return aulaService.saveAula(aulaCreate);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateAula(
            @Valid @RequestBody AulaUpdate aulaUpdate,
            BindingResult bindingResult, @PathVariable Long id) {
        Validator.checkValidBody(bindingResult);
        aulaService.updateAula(aulaUpdate, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void eliminarAula(@PathVariable Long id) {
        aulaService.eliminarAula(id);
    }
}

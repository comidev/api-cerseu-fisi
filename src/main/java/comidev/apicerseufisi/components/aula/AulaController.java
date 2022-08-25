package comidev.apicerseufisi.components.aula;

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

import comidev.apicerseufisi.components.aula.doc.AulaDoc;
import comidev.apicerseufisi.components.aula.request.*;
import comidev.apicerseufisi.components.aula.response.*;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/aulas")
@AllArgsConstructor
public class AulaController implements AulaDoc {
    private final AulaService aulaService;

    @GetMapping
    @ResponseBody
    public List<AulaDetails> getAllAulas() {
        return aulaService.getAllAulas();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public AulaDetails getAulaById(@PathVariable Long id) {
        return aulaService.getAulaById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public AulaDetails saveAula(@Valid @RequestBody AulaCreate body) {
        return aulaService.saveAula(body);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAula(@PathVariable Long id,
            @Valid @RequestBody AulaUpdate body) {
        aulaService.updateAula(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAula(@PathVariable Long id) {
        aulaService.deleteAula(id);
    }
}

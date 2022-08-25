package comidev.apicerseufisi.components.disponibilidad;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import comidev.apicerseufisi.components.disponibilidad.doc.DisponibilidadDoc;
import comidev.apicerseufisi.components.disponibilidad.request.DisponibilidadCreate;
import comidev.apicerseufisi.components.disponibilidad.request.DisponibilidadUpdate;
import comidev.apicerseufisi.components.disponibilidad.response.DisponibilidadDetails;
import comidev.apicerseufisi.components.disponibilidad.response.DisponibilidadByDocente;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/disponibilidades")
@AllArgsConstructor
public class DisponibilidadController implements DisponibilidadDoc {
    private final DisponibilidadService disponibilidadService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveDisponibilidad(
            @Valid @RequestBody DisponibilidadCreate body) {
        disponibilidadService.saveDisponibilidad(body);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDisponibilidad(
            @RequestParam(name = "docenteId") Long docenteId,
            @RequestParam(name = "cursoId") Long cursoId,
            @Valid @RequestBody DisponibilidadUpdate body) {
        disponibilidadService.updateDisponibilidad(docenteId, cursoId, body);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDisponibilidad(
            @RequestParam(name = "docenteId") Long docenteId,
            @RequestParam(name = "cursoId") Long cursoId) {
        disponibilidadService.deleteDisponibilidad(docenteId, cursoId);
    }

    @GetMapping
    @ResponseBody
    public DisponibilidadDetails getDisponibilidadByDocenteAndCurso(
            @RequestParam(name = "docenteId") Long docenteId,
            @RequestParam(name = "cursoId") Long cursoId) {
        return disponibilidadService.getDisponibilidadByDocenteAndCurso(docenteId, cursoId);
    }

    @GetMapping("/docente/{docenteId}")
    @ResponseBody
    public List<DisponibilidadByDocente> getDisponibilidadByDocente(
            @PathVariable Long docenteId) {
        return disponibilidadService.getDisponibilidadByDocente(docenteId);
    }
}

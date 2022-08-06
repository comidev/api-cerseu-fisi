package comidev.apicerseufisi.components.disponibilidad;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
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

import comidev.apicerseufisi.components.disponibilidad.request.DisponibilidadCreate;
import comidev.apicerseufisi.components.disponibilidad.request.DisponibilidadUpdate;
import comidev.apicerseufisi.components.disponibilidad.response.DisponibilidadDetails;
import comidev.apicerseufisi.components.disponibilidad.response.DisponibilidadListByDocente;
import comidev.apicerseufisi.exceptions.Validator;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/disponibilidades")
@AllArgsConstructor
public class DisponibilidadController {
    private final DisponibilidadService disponibilidadService;

    @Operation(summary = "Registra la disponibilidad del docente con un curso", description = "En función de un docente y un curso se guarda su disponibilidad")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void saveDisponibilidad(
            @Valid @RequestBody DisponibilidadCreate disponibilidadCreate,
            BindingResult bindingResult) {
        Validator.checkValidBody(bindingResult);
        disponibilidadService.saveDisponibilidad(disponibilidadCreate);
    }

    @Operation(summary = "Actualiza la disponibilidad del docente con un curso", description = "En función de un docente y un curso se actualiza su disponibilidad")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateDisponibilidad(
            @RequestParam(name = "docenteId") Long docenteId,
            @RequestParam(name = "cursoId") Long cursoId,
            @Valid @RequestBody DisponibilidadUpdate update,
            BindingResult bindingResult) {
        Validator.checkValidBody(bindingResult);
        disponibilidadService.updateDisponibilidad(docenteId, cursoId, update);
    }

    @Operation(summary = "Elimina la disponibilidad del docente con un curso", description = "En función de un docente y un curso se elimina su disponibilidad")
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deleteDisponibilidad(
            @RequestParam(name = "docenteId") Long docenteId,
            @RequestParam(name = "cursoId") Long cursoId) {
        disponibilidadService.deleteDisponibilidad(docenteId, cursoId);
    }

    @Operation(summary = "Devuelve la disponibilidad del docente con un curso", description = "En función de un docente y un curso devuelve la disponibilidad")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public DisponibilidadDetails getByDocenteAndCurso(
            @RequestParam(name = "docenteId") Long docenteId,
            @RequestParam(name = "cursoId") Long cursoId) {
        return disponibilidadService.getByDocenteAndCurso(docenteId, cursoId);
    }

    @Operation(summary = "Devuelve la disponibilidades del docente", description = "En función de un docente devuelve las disponibilidades")
    @GetMapping("/docente/{docenteId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<DisponibilidadListByDocente> getByDocente(@PathVariable Long docenteId) {
        return disponibilidadService.getByDocente(docenteId);
    }
}

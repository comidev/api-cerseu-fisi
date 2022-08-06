package comidev.apicerseufisi.components.horario;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import comidev.apicerseufisi.components.aula.response.AulaDetails;
import comidev.apicerseufisi.components.horario.request.HorarioCreate;
import comidev.apicerseufisi.components.horario.response.HorarioDetails;
import comidev.apicerseufisi.components.horario.utils.HorarioEstado;
import comidev.apicerseufisi.exceptions.Validator;
import comidev.apicerseufisi.utils.Dia;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/horarios")
@AllArgsConstructor
public class HorarioController {
    private final HorarioService horarioService;

    @Operation(summary = "Registra un horario", description = "En función de un docente y un curso se registra un horario al sistema")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void registrarHorario(
            @Valid @RequestBody HorarioCreate horarioCreate,
            BindingResult bindingResult) {
        Validator.checkValidBody(bindingResult);
        horarioService.registrarHorario(horarioCreate);
    }

    @Operation(summary = "Devuelve todos los horarios o por su estado", description = "Util para ver los horarios validados, rechazados, creados, etc.")
    @GetMapping
    public ResponseEntity<List<HorarioDetails>> getAll(
            @RequestParam(name = "estado", required = false) HorarioEstado horarioEstado) {
        List<HorarioDetails> body = horarioService.getAllOrByHorarioEstado(horarioEstado);
        return ResponseEntity.status(body.isEmpty() ? 204 : 200).body(body);
    }

    @Operation(summary = "Devuelve un horario por su id", description = "Útil para saber al detalle sobre un horaio")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public HorarioDetails getById(@PathVariable Long id) {
        return horarioService.getById(id);
    }

    @Operation(summary = "Devuelve las aulas disponibles en esa hora y dia", description = "Útil para reservar el aula o asignar un aula a un horario")
    @GetMapping("/aula")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<AulaDetails> getAllAulasByHoraAndDia(
            @RequestParam(name = "inicio") String inicio,
            @RequestParam(name = "fin") String fin,
            @RequestParam(name = "dia") Dia dia) {

        return horarioService.getAllAulasByHoraAndDia(
                Validator.checkValidTime(inicio),
                Validator.checkValidTime(fin),
                dia);
    }

    @Operation(summary = "Devuelve los horarios que tiene un aula por su id", description = "Devuelve todos los horarios de una aula por su id")
    @GetMapping("/aula/{aulaId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<HorarioDetails> getAllByAula(@PathVariable Long aulaId) {
        return horarioService.getAllByAula(aulaId);
    }

    @Operation(summary = "Reserva un aula a un horario, mediante un horario id y un aula id", description = "Reserva un aula a un horario")
    @PatchMapping("/reservar")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void reservarAula(
            @RequestParam(name = "horarioId") Long horarioId,
            @RequestParam(name = "aulaId") Long aulaId) {
        horarioService.reservarAula(horarioId, aulaId);
    }

    @Operation(summary = "Establece un estado al horario", description = "Util para validar un horario o dejarlo en 'observado'")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void setHorarioEstado(@PathVariable Long id,
            @RequestParam(name = "estado") HorarioEstado horarioEstado) {
        horarioService.setHorarioEstado(id, horarioEstado);
    }

    @Operation(summary = "Devuelve los horarios que maneja un Docente en el presente ciclo", description = "Util para  el docente vea sus cursos jsjs como en el sum :v")
    @GetMapping("/docente/{docenteId}")
    public ResponseEntity<List<HorarioDetails>> getHorariosByDocente(
            @PathVariable Long docenteId) {
        List<HorarioDetails> body = horarioService.getHorariosByDocente(docenteId);
        return ResponseEntity.status(body.isEmpty() ? 204 : 200).body(body);
    }
}

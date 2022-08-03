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
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/horarios")
@AllArgsConstructor
public class HorarioController {
    private final HorarioService horarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void registrarHorario(
            @Valid @RequestBody HorarioCreate horarioCreate,
            BindingResult bindingResult) {
        Validator.checkValidBody(bindingResult);
        horarioService.registrarHorario(horarioCreate);
    }

    @GetMapping
    public ResponseEntity<List<HorarioDetails>> getAll(
            @RequestParam(name = "estado", required = false) HorarioEstado horarioEstado) {
        List<HorarioDetails> body = horarioService.getAllOrByHorarioEstado(horarioEstado);
        return ResponseEntity.status(body.isEmpty() ? 204 : 200).body(body);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public HorarioDetails getById(@PathVariable Long id) {
        return horarioService.getById(id);
    }

    // ? Testear :v LocalTime inicio
    @GetMapping("/aula")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<AulaDetails> getAllAulasOrByHora(
            @RequestParam(name = "inicio") String inicio,
            @RequestParam(name = "fin") String fin,
            @RequestParam(name = "dia") Dia dia) {

        return horarioService.getAllAulasOrByHora(
                Validator.checkValidTime(inicio),
                Validator.checkValidTime(fin),
                dia);
    }

    @GetMapping("/aula/{aulaId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<HorarioDetails> getAllByAula(@PathVariable Long aulaId) {
        return horarioService.getAllByAula(aulaId);
    }

    @PatchMapping("/reservar")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void reservarAula(
            @RequestParam(name = "horarioId") Long horarioId,
            @RequestParam(name = "aulaId") Long aulaId) {
        horarioService.reservarAula(horarioId, aulaId);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void setHorarioEstado(@PathVariable Long id,
            @RequestParam(name = "estado") HorarioEstado horarioEstado) {
        horarioService.setHorarioEstado(id, horarioEstado);
    }

    @GetMapping("/docente/{docenteId}")
    public ResponseEntity<List<HorarioDetails>> getHorariosByDocente(
            @PathVariable Long docenteId) {
        List<HorarioDetails> body = horarioService.getHorariosByDocente(docenteId);
        return ResponseEntity.status(body.isEmpty() ? 204 : 200).body(body);
    }
}

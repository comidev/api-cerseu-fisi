package comidev.apicerseufisi.components.horario;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
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
import comidev.apicerseufisi.components.horario.doc.HorarioDoc;
import comidev.apicerseufisi.components.horario.request.HorarioCreate;
import comidev.apicerseufisi.components.horario.response.HorarioDetails;
import comidev.apicerseufisi.components.horario.utils.HorarioEstado;
import comidev.apicerseufisi.exceptions.Validator;
import comidev.apicerseufisi.utils.Dia;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/horarios")
@AllArgsConstructor
public class HorarioController implements HorarioDoc {
    private final HorarioService horarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registrarHorario(@Valid @RequestBody HorarioCreate body) {
        horarioService.registrarHorario(body);
    }

    @GetMapping
    @ResponseBody
    public List<HorarioDetails> getAllOrByHorarioEstado(
            @RequestParam(name = "estado", required = false) HorarioEstado horarioEstado) {
        return horarioService.getAllOrByHorarioEstado(horarioEstado);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public HorarioDetails getById(@PathVariable Long id) {
        return horarioService.getById(id);
    }

    @GetMapping("/aula")
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

    @GetMapping("/aula/{aulaId}")
    @ResponseBody
    public List<HorarioDetails> getAllByAula(@PathVariable Long aulaId) {
        return horarioService.getAllByAula(aulaId);
    }

    @PatchMapping("/reservar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reservarAula(@RequestParam(name = "aulaId") Long aulaId,
            @RequestParam(name = "horarioId") Long horarioId) {
        horarioService.reservarAula(horarioId, aulaId);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setHorarioEstado(@PathVariable Long id,
            @RequestParam(name = "estado") HorarioEstado horarioEstado) {
        horarioService.setHorarioEstado(id, horarioEstado);
    }

    @GetMapping("/docente/{docenteId}")
    @ResponseBody
    public List<HorarioDetails> getHorariosByDocente(
            @PathVariable Long docenteId) {
        return horarioService.getHorariosByDocente(docenteId);
    }
}

package comidev.apicerseufisi.components.horario.response;

import java.time.LocalTime;

import comidev.apicerseufisi.components.aula.Aula;
import comidev.apicerseufisi.components.disponibilidad.Disponibilidad;
import comidev.apicerseufisi.components.horario.Horario;
import comidev.apicerseufisi.utils.Dia;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HorarioDetails {
    private Long id;
    private LocalTime inicio;
    private LocalTime fin;
    private Dia dia;
    private Long docenteId;
    private Long cursoId;
    private Long aulaId;

    public HorarioDetails(Horario horario) {
        this.id = horario.getId();
        this.inicio = horario.getInicio();
        this.fin = horario.getFin();
        this.dia = horario.getDia();
        Disponibilidad disponibilidad = horario.getDisponibilidad();
        this.docenteId = disponibilidad.getDocente().getId();
        this.cursoId = disponibilidad.getCurso().getId();
        Aula aula = horario.getAula();
        this.aulaId = aula != null ? aula.getId() : null;
    }
}

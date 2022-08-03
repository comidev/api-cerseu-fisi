package comidev.apicerseufisi.components.horario;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import comidev.apicerseufisi.components.aula.Aula;
import comidev.apicerseufisi.components.disponibilidad.Disponibilidad;
import comidev.apicerseufisi.components.horario.request.HorarioCreate;
import comidev.apicerseufisi.components.horario.utils.HorarioEstado;
import comidev.apicerseufisi.exceptions.Validator;
import comidev.apicerseufisi.utils.Dia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "horarios")
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime inicio;
    private LocalTime fin;
    @Enumerated(EnumType.STRING)
    private Dia dia;
    @Enumerated(EnumType.STRING)
    private HorarioEstado horarioEstado;
    @OneToOne()
    @JoinColumn(name = "disponibilidad_id", nullable = false, unique = true)
    private Disponibilidad disponibilidad;
    @ManyToOne()
    @JoinColumn(name = "aula_id", nullable = true)
    private Aula aula;

    public Horario(HorarioCreate horarioCreate, Disponibilidad disponibilidad) {
        this.inicio = Validator.checkValidTime(horarioCreate.getInicio());
        this.fin = Validator.checkValidTime(horarioCreate.getFin());
        this.dia = horarioCreate.getDia();
        this.horarioEstado = HorarioEstado.CREADO;
        this.disponibilidad = disponibilidad;
    }
}

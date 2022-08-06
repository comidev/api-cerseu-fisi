package comidev.apicerseufisi.components.disponibilidad;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import comidev.apicerseufisi.components.curso.Curso;
import comidev.apicerseufisi.components.docente.Docente;
import comidev.apicerseufisi.components.fecha.Fecha;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "disponibilidades")
public class Disponibilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = false)
    private Docente docente;
    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fecha_id")
    private List<Fecha> fechas;

    public Disponibilidad(Long id) {
        this.id = id;
    }

    public Disponibilidad(Docente docente, Curso curso, List<Fecha> fechas) {
        this.docente = docente;
        this.curso = curso;
        this.fechas = fechas;
    }
}

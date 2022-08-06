package comidev.apicerseufisi.components.solicitud;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import comidev.apicerseufisi.components.alumno.Alumno;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "solicitudes")
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    @ManyToOne
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;
    private LocalDateTime createdAt;

    public Solicitud(Alumno alumno, String codigo) {
        this.alumno = alumno;
        this.codigo = codigo;
        this.createdAt = LocalDateTime.now();
    }

    public Solicitud(Long solicitudId) {
        this.id = solicitudId;
    }
}

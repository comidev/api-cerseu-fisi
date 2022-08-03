package comidev.apicerseufisi.components.aula;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import comidev.apicerseufisi.components.aula.request.AulaCreate;
import comidev.apicerseufisi.components.aula.request.AulaUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "aulas")
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer capacidad;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Aula(Long aulaId) {
        this.id = aulaId;
    }

    public Aula(AulaCreate aulaCreate) {
        this.capacidad = aulaCreate.getCapacidad();
        LocalDateTime ahora = LocalDateTime.now();
        this.createdAt = ahora;
        this.updatedAt = ahora;
    }

    public void update(AulaUpdate aulaUpdate) {
        this.capacidad = aulaUpdate.getCapacidad();
        this.updatedAt = LocalDateTime.now();
    }
}

package comidev.apicerseufisi.components.aula.response;

import java.sql.Timestamp;

import comidev.apicerseufisi.components.aula.Aula;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AulaDetails {
    private Long id;
    private Integer capacidad;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public AulaDetails(Aula entity) {
        this.id = entity.getId();
        this.capacidad = entity.getCapacidad();
        this.createdAt = Timestamp.valueOf(entity.getCreatedAt());
        this.updatedAt = Timestamp.valueOf(entity.getUpdatedAt());
    }
}

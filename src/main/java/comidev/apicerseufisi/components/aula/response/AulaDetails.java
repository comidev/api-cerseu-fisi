package comidev.apicerseufisi.components.aula.response;

import java.time.LocalDateTime;

import comidev.apicerseufisi.components.aula.Aula;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AulaDetails {
    private Long id;
    private Integer capacidad;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AulaDetails(Aula entity) {
        this.id = entity.getId();
        this.capacidad = entity.getCapacidad();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}

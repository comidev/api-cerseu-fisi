package comidev.apicerseufisi.components.disponibilidad.response;

import comidev.apicerseufisi.components.curso.response.CursoDetails;
import comidev.apicerseufisi.components.disponibilidad.Disponibilidad;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DisponibilidadListByDocente {
    private Long id;
    private CursoDetails curso;

    public DisponibilidadListByDocente(Disponibilidad disponibilidad) {
        this.id = disponibilidad.getId();
        this.curso = new CursoDetails(disponibilidad.getCurso());
    }
}

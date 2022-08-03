package comidev.apicerseufisi.components.fecha.dto;

import java.time.LocalTime;

import comidev.apicerseufisi.components.fecha.Fecha;
import comidev.apicerseufisi.utils.Dia;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FechaResponse {
    private Dia dia;
    private LocalTime inicio;
    private LocalTime fin;

    public FechaResponse(Fecha fecha) {
        this.dia = fecha.getDia();
        this.inicio = fecha.getInicio();
        this.fin = fecha.getFin();
    }
}

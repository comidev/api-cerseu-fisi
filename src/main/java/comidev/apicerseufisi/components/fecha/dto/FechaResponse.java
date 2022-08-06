package comidev.apicerseufisi.components.fecha.dto;

import java.sql.Time;

import comidev.apicerseufisi.components.fecha.Fecha;
import comidev.apicerseufisi.utils.Dia;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FechaResponse {
    private Dia dia;
    private Time inicio;
    private Time fin;

    public FechaResponse(Fecha fecha) {
        this.dia = fecha.getDia();
        this.inicio = Time.valueOf(fecha.getInicio());
        this.fin = Time.valueOf(fecha.getFin());
    }
}

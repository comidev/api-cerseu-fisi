package comidev.apicerseufisi.components.disponibilidad.request;

import java.util.List;

import javax.validation.Valid;

import comidev.apicerseufisi.components.fecha.dto.FechaCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilidadUpdate {
    private List<@Valid FechaCreate> fechas;
}

package comidev.apicerseufisi.components.disponibilidad.request;

import java.util.List;

import javax.validation.Valid;

import comidev.apicerseufisi.components.fecha.dto.FechaCreate;
import lombok.Getter;

@Getter
public class DisponibilidadUpdate {
    private List<@Valid FechaCreate> fechas;
}

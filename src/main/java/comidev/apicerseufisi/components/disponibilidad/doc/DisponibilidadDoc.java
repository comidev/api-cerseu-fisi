package comidev.apicerseufisi.components.disponibilidad.doc;

import java.util.List;

import comidev.apicerseufisi.components.disponibilidad.request.*;
import comidev.apicerseufisi.components.disponibilidad.response.*;
import io.swagger.v3.oas.annotations.Operation;

public interface DisponibilidadDoc {
    @Operation(summary = """
            Registra la disponibilidad del docente con un curso
            """, description = """
            En función de un docente y un curso se guarda su disponibilidad
            """)
    void saveDisponibilidad(DisponibilidadCreate body);

    @Operation(summary = """
            Actualiza la disponibilidad del docente con un curso
            """, description = """
            En función de un docente y un curso se actualiza su disponibilidad
            """)
    void updateDisponibilidad(Long docenteId, Long cursoId, DisponibilidadUpdate body);

    @Operation(summary = """
            Elimina la disponibilidad del docente con un curso
            """, description = """
            En función de un docente y un curso se elimina su disponibilidad
            """)
    void deleteDisponibilidad(Long docenteId, Long cursoId);

    @Operation(summary = """
            Devuelve la disponibilidad del docente con un curso
            """, description = """
            En función de un docente y un curso devuelve la disponibilidad
            """)
    DisponibilidadDetails getDisponibilidadByDocenteAndCurso(Long docenteId, Long cursoId);

    @Operation(summary = """
            Devuelve la disponibilidades del docente
            """, description = """
            En función de un docente devuelve las disponibilidades
            """)
    List<DisponibilidadByDocente> getDisponibilidadByDocente(Long docenteId);
}

package comidev.apicerseufisi.components.horario.doc;

import java.util.List;

import comidev.apicerseufisi.components.aula.response.AulaDetails;
import comidev.apicerseufisi.components.horario.request.*;
import comidev.apicerseufisi.components.horario.response.*;
import comidev.apicerseufisi.components.horario.utils.HorarioEstado;
import comidev.apicerseufisi.utils.Dia;
import io.swagger.v3.oas.annotations.Operation;

public interface HorarioDoc {
    @Operation(summary = """
            Registra un horario
            """, description = """
            En función de un docente y un curso se registra un horario al sistema
            """)
    void registrarHorario(HorarioCreate body);

    @Operation(summary = """
            Devuelve todos los horarios o por su estado
            """, description = """
            Util para ver los horarios validados, rechazados, creados, etc.
            """)
    List<HorarioDetails> getAllOrByHorarioEstado(HorarioEstado horarioEstado);

    @Operation(summary = """
            Devuelve un horario por su id
            """, description = """
            Útil para saber al detalle sobre un horaio
            """)
    HorarioDetails getById(Long id);

    @Operation(summary = """
            Devuelve las aulas disponibles en esa hora y dia
            """, description = """
            Útil para reservar el aula o asignar un aula a un horario
            """)
    List<AulaDetails> getAllAulasByHoraAndDia(String inicio, String fin, Dia dia);

    @Operation(summary = """
            Devuelve los horarios que tiene un aula por su id
            """, description = """
            Devuelve todos los horarios de una aula por su id
            """)
    List<HorarioDetails> getAllByAula(Long aulaId);

    @Operation(summary = """
            Reserva un aula a un horario, mediante un horario id y un aula id
            """, description = """
            Reserva un aula a un horario
            """)
    void reservarAula(Long aulaId, Long horarioId);

    @Operation(summary = """
            Establece un estado al horario
            """, description = """
            Util para validar un horario o dejarlo en 'observado'
            """)
    void setHorarioEstado(Long id, HorarioEstado horarioEstado);

    @Operation(summary = """
            Devuelve los horarios que maneja un Docente en el presente ciclo
            """, description = """
            Util para  el docente vea sus cursos jsjs como en el sum :v
            """)
    List<HorarioDetails> getHorariosByDocente(Long docenteId);
}

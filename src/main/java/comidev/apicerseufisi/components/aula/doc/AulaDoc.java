package comidev.apicerseufisi.components.aula.doc;

import java.util.List;

import comidev.apicerseufisi.components.aula.request.*;
import comidev.apicerseufisi.components.aula.response.*;
import io.swagger.v3.oas.annotations.Operation;

public interface AulaDoc {
    @Operation(summary = "Devuelve las aulas del sistema", description = "Devuelve las aulas del sistema")
    List<AulaDetails> getAllAulas();

    @Operation(summary = "Devuelve la aula del sistema según su id", description = "Devuelve la aula del sistema  según el id")
    AulaDetails getAulaById(Long id);

    @Operation(summary = "Registra el aula al sistema", description = "Registra el aula al sistema")
    AulaDetails saveAula(AulaCreate body);

    @Operation(summary = "Actualiza el aula al sistema", description = "Actualiza el aula al sistema")
    void updateAula(Long id, AulaUpdate body);

    @Operation(summary = "Elimina el aula al sistema", description = "Elimina el aula al sistema")
    void deleteAula(Long id);
}

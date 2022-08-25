package comidev.apicerseufisi.components.curso.doc;

import java.util.List;

import comidev.apicerseufisi.components.curso.request.*;
import comidev.apicerseufisi.components.curso.response.*;
import io.swagger.v3.oas.annotations.Operation;

public interface CursoDoc {
    @Operation(summary = "Devuelve los cursos del sistema", description = "Devuelve los cursos del sistema")
    List<CursoDetails> getAllCursos();

    @Operation(summary = "Devuelve el curso del sistema por id", description = "Devuelve el curso del sistema por id")
    CursoDetails getCursoById(Long id);

    @Operation(summary = "Guarda un curso en el sistema", description = "Registra un curso en el sistema")
    CursoDetails saveCurso(CursoCreate body);

    @Operation(summary = "Actualiza un curso en el sistema", description = "Actualiza un curso en el sistema")
    void updateCurso(Long id, CursoUpdate cursoUpdate);

    @Operation(summary = "Elimina un curso en el sistema", description = "Elimina un curso en el sistema")
    void eliminarCurso(Long id);
}

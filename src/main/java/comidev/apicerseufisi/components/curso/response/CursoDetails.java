package comidev.apicerseufisi.components.curso.response;

import java.sql.Timestamp;

import comidev.apicerseufisi.components.curso.Curso;
import comidev.apicerseufisi.components.curso.utils.CursoEstado;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CursoDetails {
    private Long id;
    private String nombre;
    private String codigo;
    private Integer ciclo;
    private Integer planDeEstudio;
    private Integer creditos;
    private CursoEstado cursoEstado;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public CursoDetails(Curso entity) {
        this.id = entity.getId();
        this.nombre = entity.getNombre();
        this.codigo = entity.getCodigo();
        this.ciclo = entity.getCiclo();
        this.planDeEstudio = entity.getPlanDeEstudio();
        this.creditos = entity.getCreditos();
        this.cursoEstado = entity.getCursoEstado();
        this.createdAt = Timestamp.valueOf(entity.getCreatedAt());
        this.updatedAt = Timestamp.valueOf(entity.getUpdatedAt());
    }
}

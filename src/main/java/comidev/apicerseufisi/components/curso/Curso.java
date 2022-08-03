package comidev.apicerseufisi.components.curso;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.http.HttpStatus;

import comidev.apicerseufisi.components.curso.request.CursoCreate;
import comidev.apicerseufisi.components.curso.request.CursoUpdate;
import comidev.apicerseufisi.components.curso.utils.CursoEstado;
import comidev.apicerseufisi.exceptions.HttpException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cursos")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String codigo;
    private String nombre;
    private Integer ciclo;
    private Integer planDeEstudio;
    private Integer creditos;
    @Enumerated(EnumType.STRING)
    @Column(name = "curso_estado")
    private CursoEstado cursoEstado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Curso(Long id) {
        this.id = id;
    }

    public Curso(CursoCreate cursoCreate) {
        this.nombre = cursoCreate.getNombre();
        this.codigo = cursoCreate.getCodigo();
        this.ciclo = cursoCreate.getCiclo();
        this.planDeEstudio = cursoCreate.getPlanDeEstudio();
        this.creditos = cursoCreate.getCreditos();
        this.cursoEstado = CursoEstado.NO_APERTURADO;
        LocalDateTime ahora = LocalDateTime.now();
        this.createdAt = ahora;
        this.updatedAt = ahora;
    }

    public void update(CursoUpdate cursoDTO) {
        this.nombre = cursoDTO.getNombre();
        this.codigo = cursoDTO.getCodigo();
        this.ciclo = cursoDTO.getCiclo();
        this.planDeEstudio = cursoDTO.getPlanDeEstudio();
        this.creditos = cursoDTO.getCreditos();
        this.updatedAt = LocalDateTime.now();
    }

    public void validarMonto(Float monto) {
        Float montoCurso = this.creditos * CursoService.PRECIO_POR_CREDITO;
        if (monto <= montoCurso) {
            String message = "El monto es menor -> (" + monto + "<=" + montoCurso + ")";
            throw new HttpException(HttpStatus.BAD_REQUEST, message);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Curso other = (Curso) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}

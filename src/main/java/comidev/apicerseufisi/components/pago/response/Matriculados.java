package comidev.apicerseufisi.components.pago.response;

import java.util.List;
import java.util.stream.Collectors;

import comidev.apicerseufisi.components.alumno.Alumno;
import comidev.apicerseufisi.components.curso.CursoService;
import comidev.apicerseufisi.components.curso.utils.CursoEstado;
import comidev.apicerseufisi.components.pago.Pago;
import comidev.apicerseufisi.components.usuario.response.UsuarioDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Matriculados {
    private Integer cantidad;
    private List<UsuarioDetails> alumnos;
    private CursoEstado estado;

    public Matriculados(List<Pago> pagos) {
        int size = pagos.size();
        this.cantidad = size;
        this.alumnos = pagos.stream()
                .map(item -> {
                    Alumno alumno = item.getSolicitud().getAlumno();
                    return new UsuarioDetails(alumno.getId(), alumno.getUsuario());
                })
                .collect(Collectors.toList());
        this.estado = size >= CursoService.MIN_ALUMNOS_MATRICULADOS
                ? CursoEstado.APTO
                : CursoEstado.NO_APTO;
    }
}

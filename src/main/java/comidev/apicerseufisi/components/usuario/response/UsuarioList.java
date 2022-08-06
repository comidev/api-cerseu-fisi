package comidev.apicerseufisi.components.usuario.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import comidev.apicerseufisi.components.alumno.Alumno;
import comidev.apicerseufisi.components.docente.Docente;
import comidev.apicerseufisi.components.usuario.Usuario;
import comidev.apicerseufisi.components.usuario.utils.Rol;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsuarioList {
    @JsonInclude(Include.NON_NULL)
    private Long id;
    // * ¡Obligatorio!
    private Long usuarioId;
    private String codigo;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private Rol rol;

    public UsuarioList(Docente docente) {
        this.id = docente.getId();
        initUsuario(docente.getUsuario());
    }

    public UsuarioList(Alumno alumno) {
        this.id = alumno.getId();
        initUsuario(alumno.getUsuario());
    }

    public UsuarioList(Usuario usuario) {
        initUsuario(usuario);
    }

    private void initUsuario(Usuario usuario) {
        this.usuarioId = usuario.getId();
        this.codigo = usuario.getCodigo();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.telefono = usuario.getTelefono();
        this.correo = usuario.getCorreo();
        this.rol = usuario.getRol();
    }
}

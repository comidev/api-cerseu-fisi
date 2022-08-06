package comidev.apicerseufisi.components.usuario.response;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import comidev.apicerseufisi.components.usuario.Usuario;
import comidev.apicerseufisi.components.usuario.utils.Rol;
import comidev.apicerseufisi.components.usuario.utils.Sexo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class UsuarioDetails {
    @JsonInclude(Include.NON_NULL)
    private Long id;
    // * ¡Obligatorio!
    private Long usuarioId;
    private String codigo;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private Sexo sexo;
    private String correo;
    private Rol rol;
    private Boolean activado;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public UsuarioDetails(Long id, Usuario usuario) {
        this.id = id;
        this.usuarioId = usuario.getId();
        this.codigo = usuario.getCodigo();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.dni = usuario.getDni();
        this.telefono = usuario.getTelefono();
        this.sexo = usuario.getSexo();
        this.correo = usuario.getCorreo();
        this.rol = usuario.getRol();
        this.activado = usuario.getActivado();
        this.createdAt = Timestamp.valueOf(usuario.getCreatedAt());
        this.updatedAt = Timestamp.valueOf(usuario.getUpdatedAt());
    }
}

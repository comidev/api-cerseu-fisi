package comidev.apicerseufisi.components.usuario.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import comidev.apicerseufisi.components.usuario.utils.Rol;
import comidev.apicerseufisi.components.usuario.utils.Sexo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioCreate {
    //@NotEmpty(message = "No puede ser vacio")
    private String codigo;

    @NotEmpty(message = "No puede ser vacio")
    private String nombre;

    @NotEmpty(message = "No puede ser vacio")
    private String apellido;

    @Length(min = 8, max = 8, message = "Debe tener 8 cifras")
    private String dni;

    @Length(min = 9, max = 9, message = "Debe tener 9 cifras")
    private String telefono;

    @NotNull(message = "No puede ser vacio")
    private Sexo sexo;

    @NotEmpty(message = "No puede ser vacio")
    @Email(message = "Debe tener formato de email")
    private String correo;

    @Length(min = 3, message = "Minimo 3 caracteres")
    private String password;

    @NotNull(message = "No puede ser vacio")
    private Rol rol;
}

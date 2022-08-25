package comidev.apicerseufisi.components.usuario.doc;

import java.util.List;

import comidev.apicerseufisi.components.usuario.request.*;
import comidev.apicerseufisi.components.usuario.response.*;
import comidev.apicerseufisi.components.usuario.utils.Rol;
import io.swagger.v3.oas.annotations.Operation;

public interface UserDoc {
    @Operation(summary = """
                Devuelve los usuarios del sistema
            """, description = """
            Devuelve los usuarios del sistema, puede ser en funcion de un rol o sin rol
            (es decir todos jsjsjs)
                """)
    public List<UsuarioList> getAllUsersOrByRole(Rol rol);

    @Operation(summary = """
            Devuelve el usuario por id y rol (no obligatorio)
            """, description = """
            OJO: Si coloca rol solo se permite ALUMNO y DOCENTE. Cuando coloca rol, el id
            hará referencia a la entidad de ese rol, es decir si coloca 'ALUMNO' el id
            será de la tabla Alumnos y no de Usuarios
            """)
    public UsuarioDetails getUserByIdAndOrRol(Long id, Rol rol);

    @Operation(summary = """
            Actualiza un usuario por id, password y rol?
                """, description = """
            OJO: Si coloca rol solo se permite ALUMNO y DOCENTE. Cuando coloca rol, el
            id hará referencia a la entidad de ese rol, es decir si coloca 'ALUMNO' el
            id será de la tabla Alumnos y no de Usuarios
            """)
    public void updateUser(Long id, Rol rol, UsuarioUpdate body);

    @Operation(summary = """
            Elimina un usuario por id, password y rol?
            """, description = """
            OJO: Si coloca rol solo se permite ALUMNO y DOCENTE. Cuando coloca rol,
            el id hará referencia a la entidad de ese rol, es decir si coloca
            'ALUMNO' el id será de la tabla Alumnos y no de Usuarios
            """)
    public void deleteUser(Long id, String password, Rol rol);
}

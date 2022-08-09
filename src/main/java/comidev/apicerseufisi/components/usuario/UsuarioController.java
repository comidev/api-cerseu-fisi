package comidev.apicerseufisi.components.usuario;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import comidev.apicerseufisi.components.usuario.request.UsuarioUpdate;
import comidev.apicerseufisi.components.usuario.response.UsuarioDetails;
import comidev.apicerseufisi.components.usuario.response.UsuarioList;
import comidev.apicerseufisi.components.usuario.utils.Rol;
import comidev.apicerseufisi.exceptions.Validator;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Operation(summary = "Devuelve los usuarios del sistema", description = "Devuelve los usuarios del sistema, puede ser en funcion de un rol o sin rol (es decir todos jsjsjs)")
    @GetMapping
    public ResponseEntity<List<UsuarioList>> getAll(
            @RequestParam(name = "rol", required = false) Rol rol) {
        List<UsuarioList> body = usuarioService.getAll(rol);
        return ResponseEntity.status(body.isEmpty() ? 204 : 200).body(body);
    }

    @Operation(summary = "Devuelve el usuario por id y rol (no obligatorio)", description = "OJO: Si coloca rol solo se permite ALUMNO y DOCENTE. Cuando coloca rol, el id hará referencia a la entidad de ese rol, es decir si coloca 'ALUMNO' el id será de la tabla Alumnos y no de Usuarios")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UsuarioDetails getById(@PathVariable Long id,
            @RequestParam(name = "rol", required = false) Rol rol) {
        return usuarioService.getById(id, rol);
    }

    @Operation(summary = "Actualiza un usuario por id, password y rol?", description = "OJO: Si coloca rol solo se permite ALUMNO y DOCENTE. Cuando coloca rol, el id hará referencia a la entidad de ese rol, es decir si coloca 'ALUMNO' el id será de la tabla Alumnos y no de Usuarios")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void actualizarUsuario(
            @Valid @RequestBody UsuarioUpdate usuarioUpdate,
            BindingResult bindingResult,
            @PathVariable Long id,
            @RequestParam(name = "rol", required = false) Rol rol) {
        Validator.checkValidBody(bindingResult);
        usuarioService.actualizarUsuario(usuarioUpdate, id, rol);
    }

    @Operation(summary = "Elimina un usuario por id, password y rol?", description = "OJO: Si coloca rol solo se permite ALUMNO y DOCENTE. Cuando coloca rol, el id hará referencia a la entidad de ese rol, es decir si coloca 'ALUMNO' el id será de la tabla Alumnos y no de Usuarios")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void eliminarUsuario(@PathVariable Long id,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "rol", required = false) Rol rol) {
        usuarioService.eliminarUsuario(password, id, rol);
    }
}

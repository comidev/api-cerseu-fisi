package comidev.apicerseufisi.components.usuario;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
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

import comidev.apicerseufisi.components.usuario.doc.UserDoc;
import comidev.apicerseufisi.components.usuario.request.UsuarioUpdate;
import comidev.apicerseufisi.components.usuario.response.UsuarioDetails;
import comidev.apicerseufisi.components.usuario.response.UsuarioList;
import comidev.apicerseufisi.components.usuario.utils.Rol;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController implements UserDoc {
    private final UsuarioService usuarioService;

    @GetMapping
    @ResponseBody
    public List<UsuarioList> getAllUsersOrByRole(
            @RequestParam(name = "rol", required = false) Rol rol) {
        return usuarioService.getAllUsersOrByRole(rol);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public UsuarioDetails getUserByIdAndOrRol(@PathVariable Long id,
            @RequestParam(name = "rol", required = false) Rol rol) {
        return usuarioService.getUserByIdAndOrRol(id, rol);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable Long id,
            @RequestParam(name = "rol", required = false) Rol rol,
            @Valid @RequestBody UsuarioUpdate body) {
        usuarioService.updateUser(id, rol, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "rol", required = false) Rol rol) {
        usuarioService.deleteUser(id, password, rol);
    }
}

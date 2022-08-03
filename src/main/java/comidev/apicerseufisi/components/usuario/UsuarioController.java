package comidev.apicerseufisi.components.usuario;

import java.util.List;

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
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioList>> getAll(
            @RequestParam(name = "rol", required = false) Rol rol) {
        List<UsuarioList> body = usuarioService.getAll(rol);
        return ResponseEntity.status(body.isEmpty() ? 204 : 200).body(body);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UsuarioDetails getById(@PathVariable Long id,
            @RequestParam(name = "rol", required = false) Rol rol) {
        return usuarioService.getById(id, rol);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void actualizarUsuario(
            @RequestBody UsuarioUpdate usuarioUpdate,
            BindingResult bindingResult,
            @PathVariable Long id,
            @RequestParam(name = "rol", required = false) Rol rol) {
        Validator.checkValidBody(bindingResult);
        usuarioService.actualizarUsuario(usuarioUpdate, id, rol);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void eliminarUsuario(@PathVariable Long id,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "rol", required = false) Rol rol) {
        usuarioService.eliminarUsuario(password, id, rol);
    }
}

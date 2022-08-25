package comidev.apicerseufisi.components.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import comidev.apicerseufisi.components.alumno.AlumnoService;
import comidev.apicerseufisi.components.docente.DocenteService;
import comidev.apicerseufisi.components.usuario.request.UsuarioCreate;
import comidev.apicerseufisi.components.usuario.request.UsuarioUpdate;
import comidev.apicerseufisi.components.usuario.response.UsuarioDetails;
import comidev.apicerseufisi.components.usuario.response.UsuarioList;
import comidev.apicerseufisi.components.usuario.utils.Rol;
import comidev.apicerseufisi.exceptions.HttpException;

@Getter
@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {
    private final UsuarioRepo usuarioRepo;
    private final AlumnoService alumnoService;
    private final DocenteService docenteService;
    public final BCryptPasswordEncoder bcrypt;

    public List<UsuarioList> getAllUsersOrByRole(Rol rol) {
        if (rol != null) {
            // * ¿alumno o docente?
            if (rol.equals(Rol.ALUMNO)) {
                return alumnoService.findAll().stream()
                        .map(UsuarioList::new)
                        .collect(Collectors.toList());
            }
            if (rol.equals(Rol.DOCENTE)) {
                return docenteService.findAll().stream()
                        .map(UsuarioList::new)
                        .collect(Collectors.toList());
            }
            return usuarioRepo.findByRol(rol).stream()
                    .map(UsuarioList::new)
                    .collect(Collectors.toList());
        }
        return usuarioRepo.findAll().stream()
                .map(UsuarioList::new)
                .collect(Collectors.toList());
    }

    public UsuarioDetails getUserByIdAndOrRol(Long id, Rol rol) {
        Usuario usuario = findByIdAndOrRol(id, rol);
        return new UsuarioDetails(rol != null ? id : null, usuario);
    }

    // ! OJO: Dependencia de Token, enviar email :v
    public Usuario registrarUsuario(UsuarioCreate usuarioCreate) {
        Usuario usuario = new Usuario(usuarioCreate);
        usuario.setPassword(bcrypt.encode(usuarioCreate.getPassword()));

        Usuario usuarioDB = usuarioRepo.save(usuario);

        Rol rol = usuarioCreate.getRol();
        if (rol.equals(Rol.ALUMNO)) {
            alumnoService.saveAlumno(usuarioDB);
        }
        if (rol.equals(Rol.DOCENTE)) {
            docenteService.saveDocente(usuarioDB);
        }
        return usuarioDB;
    }

    public void updateUser(Long id, Rol rol, UsuarioUpdate body) {
        Usuario usuarioDB = findByIdAndOrRol(id, rol);
        validarPassword(usuarioDB.getPassword(), body.getActualPassword());
        usuarioDB.update(body);
        usuarioDB.setPassword(bcrypt.encode(body.getNuevoPassword()));
        usuarioRepo.save(usuarioDB);
    }

    public void deleteUser(Long id, String password, Rol rol) {
        Usuario usuarioDB = findByIdAndOrRol(id, rol);
        validarPassword(usuarioDB.getPassword(), password);
        usuarioRepo.delete(usuarioDB);
    }

    private Usuario findByIdAndOrRol(Long id, Rol rol) {
        if (rol != null) {
            if (rol.equals(Rol.ALUMNO)) {
                return alumnoService.findById(id).getUsuario();
            }
            if (rol.equals(Rol.DOCENTE)) {
                return docenteService.findById(id).getUsuario();
            }
            throwErrorRol();
        }
        return findById(id);
    }

    private void throwErrorRol() {
        String message = "Si usa 'rol' solo está permitido 'ALUMNO' y 'DOCENTE',"
                + " al usar el 'rol' el id hace referencia a la entidad de ese 'rol';"
                + " si no lo usa, hará referencia al Usuario."
                + " El 'rol' no es obligatorio :D";
        throw new HttpException(HttpStatus.BAD_REQUEST, message);
    }

    private Usuario findById(Long id) {
        return usuarioRepo.findById(id).orElseThrow(() -> {
            String message = "El usuario -> (id=" + id + ") no existe!";
            throw new HttpException(HttpStatus.NOT_FOUND, message);
        });
    }

    @Override
    public UserDetails loadUserByUsername(String correo)
            throws UsernameNotFoundException {
        return this.findByCorreo(correo);
    }

    public Usuario findByCorreo(String correo) {
        return usuarioRepo.findByCorreo(correo).orElseThrow(() -> {
            String message = "La persona -> (correo=" + correo + ") no existe!";
            throw new HttpException(HttpStatus.UNAUTHORIZED, message);
        });
    }

    private void validarPassword(String passwordDB, String passwordReq) {
        boolean areEquals = bcrypt.matches(passwordReq, passwordDB);
        if (!areEquals) {
            String message = "Password incorrecto";
            throw new HttpException(HttpStatus.UNAUTHORIZED, message);
        }
    }

    public void activarUsuarioByCorreo(String correo) {
        Usuario usuario = this.findByCorreo(correo);
        usuario.activar();
        usuarioRepo.save(usuario);
    }

}

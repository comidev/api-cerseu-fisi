package comidev.apicerseufisi.components.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

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

    public List<UsuarioList> getAll(Rol rol) {
        if (rol != null) {
            // * ¿alumno o docente?
            if (rol.equals(Rol.ALUMNO)) {
                return alumnoService.findAll().stream()
                        .map(UsuarioList::new)
                        .toList();
            }
            if (rol.equals(Rol.DOCENTE)) {
                return docenteService.findAll().stream()
                        .map(UsuarioList::new)
                        .toList();
            }
            return usuarioRepo.findByRol(rol).stream()
                    .map(UsuarioList::new)
                    .toList();
        }
        return usuarioRepo.findAll().stream()
                .map(UsuarioList::new)
                .toList();
    }

    public UsuarioDetails getById(Long id, Rol rol) {
        Usuario usuario = findByIdAndRol(id, rol);
        return new UsuarioDetails(rol != null ? id : null, usuario);
    }

    // ! OJO: Dependencia de Token, enviar email :v
    public Usuario registrarUsuario(UsuarioCreate usuarioCreate) {
        Usuario usuario = new Usuario(usuarioCreate);
        usuario.setPassword(bcrypt.encode(usuarioCreate.getPassword()));

        Usuario usuarioDB = usuarioRepo.save(usuario);

        Rol rol = usuarioCreate.getRol();
        // * Guardamos alumno o docente
        if (rol.equals(Rol.ALUMNO)) {
            alumnoService.saveAlumno(usuarioDB);
        }
        if (rol.equals(Rol.DOCENTE)) {
            docenteService.saveDocente(usuarioDB);
        }
        return usuarioDB;
    }

    public void actualizarUsuario(UsuarioUpdate usuarioUpdate, Long id, Rol rol) {
        Usuario usuarioDB = findByIdAndRol(id, rol);
        validarPassword(usuarioDB.getPassword(), usuarioUpdate.getActualPassword());
        usuarioDB.update(usuarioUpdate);
        usuarioDB.setPassword(bcrypt.encode(usuarioUpdate.getNuevoPassword()));
        usuarioRepo.save(usuarioDB);
    }

    public void eliminarUsuario(String password, Long id, Rol rol) {
        Usuario usuarioDB = findByIdAndRol(id, rol);
        validarPassword(usuarioDB.getPassword(), password);
        usuarioRepo.delete(usuarioDB);
    }

    private Usuario findByIdAndRol(Long id, Rol rol) {
        if (rol != null) {
            // * ¿alumno o docente?
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

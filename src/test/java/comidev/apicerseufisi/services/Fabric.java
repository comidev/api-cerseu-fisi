package comidev.apicerseufisi.services;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import comidev.apicerseufisi.components.alumno.Alumno;
import comidev.apicerseufisi.components.alumno.AlumnoRepo;
import comidev.apicerseufisi.components.aula.Aula;
import comidev.apicerseufisi.components.aula.AulaRepo;
import comidev.apicerseufisi.components.aula.request.AulaCreate;
import comidev.apicerseufisi.components.curso.Curso;
import comidev.apicerseufisi.components.curso.CursoRepo;
import comidev.apicerseufisi.components.curso.request.CursoCreate;
import comidev.apicerseufisi.components.curso.utils.CursoEstado;
import comidev.apicerseufisi.components.disponibilidad.Disponibilidad;
import comidev.apicerseufisi.components.disponibilidad.DisponibilidadRepo;
import comidev.apicerseufisi.components.docente.Docente;
import comidev.apicerseufisi.components.docente.DocenteRepo;
import comidev.apicerseufisi.components.fecha.Fecha;
import comidev.apicerseufisi.components.fecha.dto.FechaCreate;
import comidev.apicerseufisi.components.horario.Horario;
import comidev.apicerseufisi.components.horario.HorarioRepo;
import comidev.apicerseufisi.components.horario.request.HorarioCreate;
import comidev.apicerseufisi.components.horario.utils.HorarioEstado;
import comidev.apicerseufisi.components.pago.Pago;
import comidev.apicerseufisi.components.pago.PagoRepo;
import comidev.apicerseufisi.components.pago.request.PagoCreate;
import comidev.apicerseufisi.components.pago.utils.PagoEstado;
import comidev.apicerseufisi.components.solicitud.Solicitud;
import comidev.apicerseufisi.components.solicitud.SolicitudRepo;
import comidev.apicerseufisi.components.token.TokenConfirmacion;
import comidev.apicerseufisi.components.token.TokenRepo;
import comidev.apicerseufisi.components.usuario.Usuario;
import comidev.apicerseufisi.components.usuario.UsuarioRepo;
import comidev.apicerseufisi.components.usuario.request.UsuarioCreate;
import comidev.apicerseufisi.components.usuario.utils.Rol;
import comidev.apicerseufisi.components.usuario.utils.Sexo;
import comidev.apicerseufisi.utils.Dia;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class Fabric {
    private final UsuarioRepo usuarioRepo;
    private final BCryptPasswordEncoder encoder;
    private final AlumnoRepo alumnoRepo;
    private final DocenteRepo docenteRepo;
    private final TokenRepo tokenRepo;
    private final CursoRepo cursoRepo;
    private final AulaRepo aulaRepo;
    private final DisponibilidadRepo disponibilidadRepo;
    private final HorarioRepo horarioRepo;
    private final SolicitudRepo solicitudRepo;
    private final PagoRepo pagoRepo;

    public String uuid() {
        return UUID.randomUUID().toString();
    }

    public Usuario createUsuario(Rol rol, String password) {
        if (rol == null)
            rol = Rol.ALUMNO;
        String passwordHash = password != null ? encoder.encode(password) : "password";
        return usuarioRepo.save(new Usuario(new UsuarioCreate(
                uuid(), "nombre", "apellido",
                uuid().substring(0, 8),
                uuid().substring(0, 9),
                Sexo.MASCULINO, uuid() + "@gmail.com", passwordHash, rol)));
    }

    public Alumno createAlumno(String password) {
        return alumnoRepo.save(new Alumno(createUsuario(Rol.ALUMNO, password)));
    }

    public Docente createDocente(String password) {
        return docenteRepo.save(new Docente(createUsuario(Rol.DOCENTE, password)));
    }

    public TokenConfirmacion createToken(String token, Usuario usuario) {
        if (token == null)
            token = uuid();
        if (usuario == null)
            usuario = createUsuario(null, null);
        return tokenRepo.save(new TokenConfirmacion(token, usuario));
    }

    public Curso createCurso(String codigo, CursoEstado estado) {
        if (codigo == null) {
            codigo = uuid();
        }
        Curso curso = new Curso(new CursoCreate("nombre",
                codigo, 7, 2018, 4));
        if (estado != null) {
            curso.setCursoEstado(estado);
        }
        return cursoRepo.save(curso);
    }

    public Aula createAula() {
        return aulaRepo.save(new Aula(new AulaCreate(50)));
    }

    public Fecha createFecha() {
        return new Fecha(new FechaCreate(Dia.LUNES, "18:00", "22:00"));
    }

    public Disponibilidad createDisponibilidad(Docente docente,
            Curso curso, Fecha fecha) {
        if (docente == null) {
            docente = createDocente(null);
        }
        if (curso == null) {
            curso = createCurso(null, null);
        }
        if (fecha == null) {
            fecha = createFecha();
        }
        return disponibilidadRepo.save(new Disponibilidad(
                docente, curso, List.of(fecha)));
    }

    public Horario createHorario(Curso curso, Docente docente,
            Aula aula, Fecha fecha, HorarioEstado horarioEstado) {
        if (curso == null) {
            curso = createCurso(null, null);
        }
        if (docente == null) {
            docente = createDocente(null);
        }
        if (fecha == null) {
            fecha = createFecha();
        }
        Disponibilidad disponibilidad = createDisponibilidad(docente, curso, fecha);
        HorarioCreate horarioCreate = new HorarioCreate(curso.getId(),
                docente.getId(), "18:00", "22:00", Dia.LUNES);
        Horario horario = new Horario(horarioCreate, disponibilidad);
        if (aula != null) {
            horario.setAula(aula);
        }
        if (horarioEstado != null) {
            horario.setHorarioEstado(horarioEstado);
        }
        return horarioRepo.save(horario);
    }

    public Solicitud createSolicitud(Alumno alumno, String codigo) {
        if (alumno == null) {
            alumno = createAlumno(null);
        }
        if (codigo == null) {
            codigo = uuid();
        }
        return solicitudRepo.save(new Solicitud(alumno, codigo));
    }

    public Pago createPago(Curso curso, Solicitud solicitud,
            Horario horario, PagoEstado pagoEstado) {
        if (curso == null) {
            curso = createCurso(null, null);
        }
        PagoCreate pago = new PagoCreate(curso.getMonto(), curso.getCodigo());
        if (solicitud == null) {
            solicitud = createSolicitud(null, null);
        }
        Pago pagoDB = new Pago(pago, solicitud);
        if (horario != null) {
            pagoDB.setHorario(horario);
        }
        if (pagoEstado != null) {
            pagoDB.setPagoEstado(pagoEstado);
        }
        return pagoRepo.save(pagoDB);
    }
}

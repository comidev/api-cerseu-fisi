package comidev.apicerseufisi.components.usuario;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import comidev.apicerseufisi.components.usuario.request.UsuarioCreate;
import comidev.apicerseufisi.components.usuario.request.UsuarioUpdate;
import comidev.apicerseufisi.components.usuario.utils.Rol;
import comidev.apicerseufisi.components.usuario.utils.Sexo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(nullable = false, unique = true)
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sexo sexo;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    @Column(nullable = false)
    private Boolean activado;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Usuario(UsuarioCreate personaDTO) {
        this.codigo = personaDTO.getCodigo();
        this.nombre = personaDTO.getNombre();
        this.apellido = personaDTO.getApellido();
        this.dni = personaDTO.getDni();
        this.telefono = personaDTO.getTelefono();
        this.sexo = personaDTO.getSexo();
        this.correo = personaDTO.getCorreo();

        this.password = personaDTO.getPassword();
        this.rol = personaDTO.getRol();

        LocalDateTime ahora = LocalDateTime.now();
        this.createdAt = ahora;
        this.updatedAt = ahora;

        // ! FALSE -> Mandamos notificacion por email con RabbitMQ
        this.activado = true;
    }

    public void update(UsuarioUpdate personaDTO) {
        this.codigo = personaDTO.getCodigo();
        this.nombre = personaDTO.getNombre();
        this.apellido = personaDTO.getApellido();
        this.dni = personaDTO.getDni();
        this.telefono = personaDTO.getTelefono();
        this.sexo = personaDTO.getSexo();
        this.correo = personaDTO.getCorreo();

        this.updatedAt = LocalDateTime.now();
    }

    public void activar() {
        this.activado = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.toString()));
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isEnabled() {
        return activado;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}

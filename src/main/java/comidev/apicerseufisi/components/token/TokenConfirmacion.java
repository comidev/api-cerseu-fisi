package comidev.apicerseufisi.components.token;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import comidev.apicerseufisi.components.usuario.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tokens_confirmacion")
public class TokenConfirmacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    @Column(name = "confirmed_at", nullable = true)
    private LocalDateTime confirmedAt;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public TokenConfirmacion(String token, Usuario usuario) {
        this.token = token;
        this.usuario = usuario;
        LocalDateTime ahora = LocalDateTime.now();
        this.createdAt = ahora;
        this.expiresAt = ahora.plusMinutes(15);
    }

    public void confirmar() {
        this.confirmedAt = LocalDateTime.now();
    }

    public boolean isConfirmed() {
        return this.confirmedAt != null;
    }

    public boolean expired() {
        return this.expiresAt.isBefore(LocalDateTime.now());
    }
}

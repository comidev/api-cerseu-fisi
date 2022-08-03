package comidev.apicerseufisi.components.notificacion;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import comidev.apicerseufisi.components.contenido.Contenido;
import comidev.apicerseufisi.components.notificacion.request.NotificacionCreate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "notificaciones")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String toEmail;
    private String tema;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contenido_id")
    private Contenido contenido;
    private LocalDateTime createdAt;

    public Notificacion(NotificacionCreate dto) {
        this.toEmail = dto.getToEmail();
        this.tema = dto.getTema();
        this.contenido = new Contenido(dto.getContenido());
        this.createdAt = LocalDateTime.now();
    }
}

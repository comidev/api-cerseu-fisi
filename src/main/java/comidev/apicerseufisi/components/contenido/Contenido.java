package comidev.apicerseufisi.components.contenido;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import comidev.apicerseufisi.components.contenido.request.ContenidoCreate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "contenidos")
@NoArgsConstructor
public class Contenido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String cabecera;
    private String cuerpo;
    private String enlace;
    private String enlaceName;
    private String pie;

    public Contenido(ContenidoCreate dto) {
        this.title = dto.getTitle();
        this.cabecera = dto.getCabecera();
        this.cuerpo = dto.getCuerpo();
        this.enlace = dto.getEnlace();
        this.enlaceName = dto.getEnlaceName();
        this.pie = dto.getPie();
    }
}

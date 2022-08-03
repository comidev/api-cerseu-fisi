package comidev.apicerseufisi.components.contenido.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContenidoCreate {
    private String title;
    private String cabecera;
    private String cuerpo;
    private String enlace;
    private String enlaceName;
    private String pie;
}

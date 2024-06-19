package org.Dengue.com.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paciente {
    private int id;
    private String nombre;
    private String apellido;
    private String tdocumento;
    private int ndocumento;
    private  String direccion;
    private int telefono;
    private String estado;
    private Ciudad ciudad;
}

package org.Dengue.com.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String tdocumento;
    private int ndocumento;
    private int telefono;
    private String direccion;
    private String provincia;
    private String cargo;
    private String correo;
    private String contra;
}

package org.willkadengue.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paciente implements Serializable {
    private static final long serialVersionUID = 1L;

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

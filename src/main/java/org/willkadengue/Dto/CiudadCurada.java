package org.willkadengue.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CiudadCurada implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nombre;
    private int cantidad;
}

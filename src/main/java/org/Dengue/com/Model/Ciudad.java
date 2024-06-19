package org.Dengue.com.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ciudad {
    private  int id;
    private String nombre;
    private String descripcion;
}

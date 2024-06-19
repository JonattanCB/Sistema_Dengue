package org.Dengue.com.controlladores;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import org.Dengue.com.Model.Ciudad;
import org.Dengue.com.Servicios.ServicioCiudad;
import org.Dengue.com.Servicios.ServicioPaciente;
import org.primefaces.PrimeFaces;
import org.primefaces.util.LangUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

@Data
@Named
@SessionScoped
public class BeansCiudades implements Serializable {

    @Inject
    private ServicioCiudad servicioCiudad;

    @Inject
    private ServicioPaciente servicioPaciente;

    private List<Ciudad> ciudadList;
    private List<Ciudad> ciudadListSelect;
    private Ciudad ciudad;
    private int idCiudad;

    @PostConstruct
    public  void init(){

    }

    public String irCiudad(){
        ciudadList = servicioCiudad.listTodo();
        return "ciudad";
    }


    public void nuevaCiudad(){
        ciudad = new Ciudad();
    }

    public void editarCiudad(){
        ciudad = servicioCiudad.buscar(idCiudad);
    }

    public void eliminarCiudad(){
        servicioCiudad.eliminar(idCiudad);
        servicioPaciente.deletePacienteCiudad(idCiudad);
        ciudadList = servicioCiudad.listTodo();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-user");
    }

    public void guardar(){
        if (ciudad.getId() == 0){
            servicioCiudad.agregar(ciudad);
        }else{
            servicioCiudad.actualizar(ciudad);
        }
        ciudadList = servicioCiudad.listTodo();
        PrimeFaces.current().executeScript("PF('userdialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-user");
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }
        Ciudad c = (Ciudad) value;
        return c.getNombre().toLowerCase().contains(filterText);
    }


}

package org.willkadengue.controladores;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import org.willkadengue.model.Ciudad;
import org.willkadengue.servicios.ServicioCiudad;
import org.willkadengue.servicios.ServicioPaciente;
import org.primefaces.PrimeFaces;
import org.primefaces.util.LangUtils;

import java.io.Serializable;
import java.util.List;

@Data
@Named("BeansCiudad")
@SessionScoped
public class BeansCiudad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ServicioCiudad servicioCiudad;

    @Inject
    private ServicioPaciente servicioPaciente;

    private  List<Ciudad> ciudadList;
    private List<Ciudad> ciudadListSelect;
    private Ciudad ciudad;
    private int idCiudad;

    @PostConstruct
    public  void init(){
        ciudadList = servicioCiudad.listTodo();
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
        PrimeFaces.current().executeScript("PF('userdialog').hide()"); //cambiar
        PrimeFaces.current().ajax().update("form:messages", "form:dt-user");
    }

    public boolean globalFilterFunction(Object value, Object filter) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }
        Ciudad c = (Ciudad) value;
        return c.getNombre().toLowerCase().contains(filterText);
    }


}

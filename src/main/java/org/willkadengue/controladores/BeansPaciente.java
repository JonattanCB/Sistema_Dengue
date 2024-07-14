package org.willkadengue.controladores;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import org.willkadengue.model.Ciudad;
import org.willkadengue.servicios.ServicioCiudad;
import org.willkadengue.servicios.ServicioPaciente;
import org.willkadengue.model.Paciente;
import org.primefaces.PrimeFaces;
import org.primefaces.util.LangUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

@Data
@Named("BeansPaciente")
@SessionScoped
public class BeansPaciente  implements Serializable {

    @Inject
    private ServicioPaciente servicioPaciente;

    @Inject
    private ServicioCiudad servicioCiudad;

    private List<Paciente> lstPaciente;
    private List<Paciente> lstSeleccionarPaciente;
    private  List<Ciudad> lstCiudades;
    private Paciente paciente;
    private  int id;
    private  int idCiudad;
    private String td;
    private boolean verDatosview;
    private boolean escribirDatos;
    private int idEstado;

    @PostConstruct
    private void  init() {
        /* TODO document why this method is empty */
    }

    public String irPaciente(){
        lstPaciente = servicioPaciente.listTodo();
        return "paciente";
    }

    public String irEstadosPaciente(){
        lstPaciente = servicioPaciente.listTodo();
        return "estado";
    }

    public void estadonormal(){
        servicioPaciente.cambiarEstado(idEstado, "normal");
        lstPaciente = servicioPaciente.listTodo();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-user");
    }


    public void estadocurado(){
        servicioPaciente.cambiarEstado(idEstado, "curado");
        lstPaciente = servicioPaciente.listTodo();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-user");
    }

    public void estadoinfectado(){
        servicioPaciente.cambiarEstado(idEstado, "infectado");
        lstPaciente = servicioPaciente.listTodo();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-user");
    }

    public void estadomuerto(){
        servicioPaciente.cambiarEstado(idEstado, "muerto");
        lstPaciente = servicioPaciente.listTodo();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-user");
    }



    public void nuevoPaciente(){
        lstCiudades = servicioCiudad.listTodo();
        verificar(1);
        td = "";
        idCiudad=0;
        paciente = new Paciente();
    }

    public void editarPaciente(){
        lstCiudades = servicioCiudad.listTodo();
        verificar(1);
        paciente = servicioPaciente.buscar(id);
        idCiudad = paciente.getCiudad().getId();
        td = paciente.getTdocumento();
    }

    public void eliminar(){
        servicioPaciente.eliminar(id);
        lstPaciente = servicioPaciente.listTodo();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-user");
    }

    public void verPaciente(){
        verificar(2);
        paciente = servicioPaciente.buscar(id);
    }

    public void guardar(){
        if (paciente.getId() == 0){
            paciente.setEstado("normal");
            paciente.setCiudad(servicioCiudad.buscar(idCiudad));
            paciente.setTdocumento(td);
            servicioPaciente.agregar(paciente);
        }else{
            paciente.setCiudad(servicioCiudad.buscar(idCiudad));
            paciente.setTdocumento(td);
            servicioPaciente.actualizar(paciente);
        }
        lstPaciente = servicioPaciente.listTodo();
        PrimeFaces.current().executeScript("PF('userdialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-user");
    }


    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }

        Paciente p = (Paciente) value;
        return (p.getNombre()+" "+p.getApellido()).toLowerCase().contains(filterText);
    }



    private void verificar(int opcion){
        switch (opcion){
            case 1:
                escribirDatos=true;
                verDatosview=false;
                break;
            case 2:
                escribirDatos = false;
                verDatosview = true;
                break;
        }
    }


}

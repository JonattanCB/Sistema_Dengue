package org.Dengue.com.controlladores;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import org.Dengue.com.Model.CiudadInfectada;
import org.Dengue.com.Model.Paciente;
import org.Dengue.com.Model.Usuario;
import org.Dengue.com.Servicios.ServicioPaciente;
import org.Dengue.com.Servicios.ServicioUsuario;
import org.primefaces.PrimeFaces;
import org.primefaces.util.LangUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

@Data
@Named
@SessionScoped
public class beansUsuario implements Serializable {

    @Inject
    private ServicioUsuario servicioUsuario;

    @Inject
    private ServicioPaciente servicioPaciente;

    private Usuario usuario_ingresado;
    private Usuario usuario;
    private String Url;
    private String tDocumentos;
    private int cantPersonal;
    private int cantPaciente;
    private int cantCurados;
    private int cantInfectado;
    private int idUsuario;
    private List<Paciente> lstPaciente;
    private List<CiudadInfectada> lstCiudadInfectada;
    private List<Usuario> lstUsuario;
    private List<Usuario> lstUsuarioSeleccionado;

    private  boolean verUsuarioVista;
    private boolean escribirUsuario;

    @PostConstruct
    private void init(){
        usuario_ingresado = new Usuario();
        Url = "Panel.xhtml";
    }

    public String verificiarIngreso(){
        if (servicioUsuario.verificiarUsuario(usuario_ingresado)){
            Usuario u = new Usuario();
            u.setCorreo(usuario_ingresado.getCorreo());
            u.setContra(usuario_ingresado.getContra());
            usuario_ingresado = servicioUsuario.buscarUsuarioCorreoContrasenia(u);
            return "Gestion/Dashboard";
        }else {
            usuario_ingresado = new Usuario();
            return null;
        }
    }

    public void salir(){
        try {
            usuario_ingresado = new Usuario();
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  String irDashboard() {
        cantPersonal = servicioUsuario.cantidadUsuarios();
        cantPaciente = servicioPaciente.cantidadPaciente();
        cantCurados = servicioPaciente.cantidadCurados();
        cantInfectado = servicioPaciente.cantidadInfectado();
        lstPaciente =  servicioPaciente.listTodo();
        lstCiudadInfectada = servicioPaciente.lstinfectada();
        return  "Panel";
    }

    public String irUsuario(){
        usuario = new Usuario();
        lstUsuario = servicioUsuario.listTodo();
        return "Usuario";
    }

    public void nuevoUsuario(){
        verificar(1);
        tDocumentos = "";
        usuario = new Usuario();
    }

    public void editar(){
        verificar(1);
        usuario = servicioUsuario.buscar(idUsuario);
        tDocumentos = usuario.getTdocumento();
    }

    public void eliminar(){
        servicioUsuario.eliminar(idUsuario);
        lstUsuario = servicioUsuario.listTodo();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-user");
    }

    public void verUsuario(){
        verificar(2);
        usuario = servicioUsuario.buscar(idUsuario);
    }

    public void guardar(){
        if (usuario.getId() == 0){
            usuario.setTdocumento(tDocumentos);
            servicioUsuario.agregar(usuario);
        }else{
            usuario.setTdocumento(tDocumentos);
            servicioUsuario.actualizar(usuario);
        }
        lstUsuario = servicioUsuario.listTodo();
        PrimeFaces.current().executeScript("PF('userdialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-user");
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }
        int filterInt = getInteger(filterText);

        Usuario u = (Usuario) value;
        return (u.getNombre()+" "+u.getApellido()).toLowerCase().contains(filterText);
    }

    private int getInteger(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }

    private void verificar(int opcion){
        switch (opcion){
            case 1:
                escribirUsuario=true;
                verUsuarioVista=false;
                break;
            case 2:
                escribirUsuario = false;
                verUsuarioVista = true;
                break;
        }
    }
}

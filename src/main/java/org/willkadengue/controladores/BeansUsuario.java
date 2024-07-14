package org.willkadengue.controladores;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import org.willkadengue.Dto.CiudadInfectada;
import org.willkadengue.model.Usuario;
import org.willkadengue.servicios.ServicioPaciente;
import org.willkadengue.servicios.ServicioUsuario;
import org.willkadengue.model.Paciente;
import org.primefaces.PrimeFaces;
import org.primefaces.util.LangUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Data
@Named("beansUsuario")
@SessionScoped
public class BeansUsuario implements Serializable {

    @Inject
    private ServicioUsuario servicioUsuario;

    @Inject
    private ServicioPaciente servicioPaciente;

    private Usuario usuarioingresado;
    private Usuario usuario;
    private String url;
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
        usuarioingresado = new Usuario();
        cantPersonal = servicioUsuario.cantidadUsuarios();
        cantPaciente = servicioPaciente.cantidadPaciente();
        cantCurados = servicioPaciente.cantidadCurados();
        cantInfectado = servicioPaciente.cantidadInfectado();
        lstPaciente =  servicioPaciente.listTodo();
        lstCiudadInfectada = servicioPaciente.lstinfectada();
        url = "http://localhost:8080/Proyecto-Dengue/Gestion/Panel.xhtml";
    }

    public String verificiarIngreso(){
        if (servicioUsuario.verificiarUsuario(usuarioingresado)){
            Usuario u = new Usuario();
            u.setCorreo(usuarioingresado.getCorreo());
            u.setContra(usuarioingresado.getContra());
            usuarioingresado = servicioUsuario.buscarUsuarioCorreoContrasenia(u);
            return "Gestion/Dashboard";
        }else {
            usuarioingresado = new Usuario();
            return null;
        }
    }

    public void salir(){
        try {
            usuarioingresado = new Usuario();
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
        } catch (IOException e) {
            System.out.println(e.getMessage());
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

    public boolean globalFilterFunction(Object value, Object filter) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }
        Usuario u = (Usuario) value;
        return (u.getNombre()+" "+u.getApellido()).toLowerCase().contains(filterText);
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

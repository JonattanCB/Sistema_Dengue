package org.Dengue.com.Servicios;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import org.Dengue.com.DAO.Implementos.ImplPaciente;
import org.Dengue.com.DAO.daoPacienties;
import org.Dengue.com.Model.CiudadInfectada;
import org.Dengue.com.Model.Paciente;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class ServicioPaciente implements Serializable {

    private final daoPacienties dao;

    public ServicioPaciente() {
        dao = new ImplPaciente();
    }

    public void agregar(Paciente paciente){
        dao.agregar(paciente);
    }
    public void actualizar(Paciente paciente){
        dao.actualizar(paciente);
    }
    public List<Paciente> listTodo(){
        return  dao.listTodo();
    }
    public Paciente buscar(int id){
        return  dao.buscar(id);
    }
    public void eliminar(int id){
        dao.eliminar(id);
    }

    public int cantidadPaciente(){
        return dao.cantidadPaciente();
    }

    public int cantidadInfectado(){
        return  dao.cantidadInfectados();
    }

    public int cantidadCurados(){
        return  dao.cantidadCurados();
    }

    public List<CiudadInfectada> lstinfectada(){
        return dao.listInfectada();
    }

    public void deletePacienteCiudad(int idCiudad){
        dao.eliminarPacientexCiudad(idCiudad);
    }

    public void cambiarEstado(int id, String estado){
        dao.cambiarEstado(id,estado);
    }

}

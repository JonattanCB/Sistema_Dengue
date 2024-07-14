package org.willkadengue.servicios;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import org.willkadengue.Dto.CiudadCurada;
import org.willkadengue.Dto.CiudadInfectada;
import org.willkadengue.dao.daoCiudad;
import org.willkadengue.dao.implementos.ImplCiudad;
import org.willkadengue.model.Ciudad;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class ServicioCiudad  implements Serializable {

    private  final daoCiudad dao;

    public ServicioCiudad() {
        dao = new ImplCiudad();
    }

    public  void agregar(Ciudad c){
        dao.agregar(c);
    }

    public void actualizar(Ciudad c){
        dao.actualizar(c);
    }

    public List<Ciudad> listTodo(){
        return dao.listTodo();
    }

    public Ciudad  buscar(int id){
        return  dao.buscar(id);
    }

    public void eliminar(int id){
        dao.eliminar(id);
    }

    public List<CiudadCurada> listCurada(){ return dao.getCiudadesCiudadCuradaList();}

    public List<CiudadInfectada> listInfectada(){return dao.getCiudadesCiudadInfectadaList();}
}

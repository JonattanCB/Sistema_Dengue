package org.Dengue.com.Servicios;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import org.Dengue.com.DAO.Implementos.ImplCiudad;
import org.Dengue.com.DAO.daoCiudad;
import org.Dengue.com.Model.Ciudad;

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

}

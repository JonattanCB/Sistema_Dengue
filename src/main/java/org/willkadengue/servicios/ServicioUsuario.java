package org.willkadengue.servicios;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import org.willkadengue.dao.implementos.ImplUsuario;
import org.willkadengue.dao.daoUsuario;
import org.willkadengue.model.Usuario;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class ServicioUsuario implements Serializable {

    private final daoUsuario dao;

    public ServicioUsuario() {
        dao = new ImplUsuario();
    }

    public void agregar(Usuario usuario){
        dao.agregar(usuario);
    }

    public void actualizar(Usuario usuario){
        dao.actualizar(usuario);
    }

    public List<Usuario> listTodo(){
        return  dao.listTodo();
    }

    public Usuario buscar(int id){
      return   dao.buscar(id);
    }

    public void eliminar(int id){
        dao.eliminar(id);
    }

    public boolean verificiarUsuario(Usuario usuario){
        return  dao.verificarAcceso(usuario);
    }

    public int cantidadUsuarios (){
        return  dao.cantidadPersonal();
    }

    public Usuario buscarUsuarioCorreoContrasenia(Usuario usuario){
        return  dao.BuscarUsuario(usuario);
    }
}

package org.Dengue.com.DAO;

import org.Dengue.com.Model.Usuario;

public interface daoUsuario extends Crud<Usuario> {

    boolean verificarAcceso(Usuario u);
    int cantidadPersonal();
    Usuario BuscarUsuario(Usuario u);
}

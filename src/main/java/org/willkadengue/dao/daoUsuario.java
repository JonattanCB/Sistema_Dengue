package org.willkadengue.dao;

import org.willkadengue.model.Usuario;

public interface daoUsuario extends Crud<Usuario> {

    boolean verificarAcceso(Usuario u);
    int cantidadPersonal();
    Usuario BuscarUsuario(Usuario u);
}

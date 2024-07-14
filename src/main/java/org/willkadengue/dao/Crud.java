package org.willkadengue.dao;

import java.util.List;

public interface Crud <T>{

    void agregar(T t);
    void actualizar(T t);
    List<T> listTodo();
    T buscar(int id);
    void eliminar(int id);
}

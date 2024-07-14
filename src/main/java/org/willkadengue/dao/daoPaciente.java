package org.willkadengue.dao;

import org.willkadengue.Dto.CiudadInfectada;
import org.willkadengue.model.Paciente;

import java.util.List;

public interface daoPaciente extends Crud<Paciente> {
    int cantidadPaciente();
    int cantidadInfectados();
    int cantidadCurados();
    List<CiudadInfectada> listInfectada();
    void eliminarPacientexCiudad(int idCiudad);
    void cambiarEstado(int id, String estado);
}

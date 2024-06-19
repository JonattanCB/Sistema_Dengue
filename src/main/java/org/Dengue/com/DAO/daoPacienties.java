package org.Dengue.com.DAO;

import org.Dengue.com.Model.CiudadInfectada;
import org.Dengue.com.Model.Paciente;

import java.util.List;

public interface daoPacienties extends  Crud<Paciente> {
    int cantidadPaciente();
    int cantidadInfectados();
    int cantidadCurados();
    List<CiudadInfectada> listInfectada();
    void eliminarPacientexCiudad(int idCiudad);
    void cambiarEstado(int id, String estado);
}

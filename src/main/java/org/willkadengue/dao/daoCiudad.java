package org.willkadengue.dao;

import org.willkadengue.Dto.CiudadCurada;
import org.willkadengue.Dto.CiudadInfectada;
import org.willkadengue.model.Ciudad;

import java.util.List;

public interface daoCiudad extends Crud<Ciudad> {
    List<CiudadInfectada> getCiudadesCiudadInfectadaList();
    List<CiudadCurada> getCiudadesCiudadCuradaList();
}

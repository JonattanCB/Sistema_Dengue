package org.willkadengue.controladores;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.willkadengue.Dto.CiudadInfectada;
import org.willkadengue.Dto.CiudadPrediccion;
import org.willkadengue.servicios.ServicioCiudad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Named("BeansPreduccion")
@SessionScoped
public class BeansPrediccion implements Serializable {

    @Inject
    private ServicioCiudad servicioCiudad;

    List<CiudadPrediccion> ListaPrediccion;

    private BarChartModel barModel;

    @PostConstruct
    private void init(){

    }

    public  String irPreduccion(){
        predecir();
        createBar();
        return "prediccion";
    }


    private void createBar(){
        barModel = new BarChartModel();
        ChartData data = new ChartData();
        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Prediccion a 5 años");

        List<Number> nPredecir = new ArrayList<>();
        List<String> cPredecir = new ArrayList<>();
        for (CiudadPrediccion p : ListaPrediccion){
            nPredecir.add(p.getCantidad());
            cPredecir.add(p.getNombre());
        }
        barDataSet.setData(nPredecir);
        data.addChartDataSet(barDataSet);
        data.setLabels(cPredecir);
        barModel.setData(data);


    }

    private void predecir(){
        ListaPrediccion = new ArrayList<>();
        List<CiudadInfectada> lstInfectadas = servicioCiudad.listInfectada();
        double tasaCreciomiento = 0.05;
        int years = 5;
        int id =1;
        for (CiudadInfectada list : lstInfectadas){
            CiudadPrediccion  ciudadPrediccion = new CiudadPrediccion();
            double infectados = predictInfected(list.getCantidad(),tasaCreciomiento, years);
            ciudadPrediccion.setId(id++);
            ciudadPrediccion.setCantidad(infectados);
            ciudadPrediccion.setNombre(list.getNombre());
            ListaPrediccion.add(ciudadPrediccion);
        }
    }


    private static double predictInfected(double initialInfected, double growthRate, int years) {
        return initialInfected * Math.exp(growthRate * years);
    }

}

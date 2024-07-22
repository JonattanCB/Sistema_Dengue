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

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
        bgColor.add("rgba(54, 162, 235, 0.2)");
        bgColor.add("rgba(153, 102, 255, 0.2)");
        bgColor.add("rgba(201, 203, 207, 0.2)");
        barDataSet.setBackgroundColor(bgColor);


        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 205, 86)");
        borderColor.add("rgb(75, 192, 192)");
        borderColor.add("rgb(54, 162, 235)");
        borderColor.add("rgb(153, 102, 255)");
        borderColor.add("rgb(201, 203, 207)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);



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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.mb;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;

@ManagedBean
@ViewScoped
public class ChartView implements Serializable {
        
    private static final String datada1 = "30/07/15";
    private static final String datada2 = "31/07/15";
    private static final String datada3 = "01/08/15";
    private static final String datada4 = "02/08/15";
    private static final String datada5 = "03/08/15";
    
    private LineChartModel multiAxisModel;
    private LineChartModel lineModel1;
    private LineChartModel lineModel2;
    private DonutChartModel donutModel1;
    private DonutChartModel donutModel2;
    private BarChartModel barModel;
    private HorizontalBarChartModel horizontalBarModel;
    private StreamedContent chart;
    
    public void init() {
        createMultiAxisModel();
        createLineModels();
        createDonutModels();
        createBarModels();        
    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries total = new ChartSeries();
        total.setLabel("Total");
        total.set(datada1, 490);
        total.set(datada2, 470);
        total.set(datada3, 398);
        total.set(datada4, 455);
        total.set(datada5, 500);

        ChartSeries pendentes = new ChartSeries();
        pendentes.setLabel("Pendentes");
        pendentes.set(datada1, 4);
        pendentes.set(datada2, 5);
        pendentes.set(datada3, 1);
        pendentes.set(datada4, 2);
        pendentes.set(datada5, 3);

        ChartSeries desfeitas = new ChartSeries();
        desfeitas.setLabel("Desfeitas");
        desfeitas.set(datada1, 2);
        desfeitas.set(datada2, 1);
        desfeitas.set(datada3, 2);
        desfeitas.set(datada4, 0);
        desfeitas.set(datada5, 2);

        ChartSeries negadas = new ChartSeries();
        negadas.setLabel("Negadas");
        negadas.set(datada1, 1);
        negadas.set(datada2, 0);
        negadas.set(datada3, 2);
        negadas.set(datada4, 0);
        negadas.set(datada5, 1);

        ChartSeries confirmadas = new ChartSeries();
        confirmadas.setLabel("Confirmadas");
        confirmadas.set(datada1, 486);
        confirmadas.set(datada2, 469);
        confirmadas.set(datada3, 395);
        confirmadas.set(datada4, 453);
        confirmadas.set(datada5, 499);

        model.addSeries(total);
        model.addSeries(pendentes);
        model.addSeries(desfeitas);
        model.addSeries(negadas);
        model.addSeries(confirmadas);

        return model;
    }

    private void createBarModels() {
        createBarModel();
        createHorizontalBarModel();
    }

    private void createBarModel() {
        barModel = initBarModel();

        barModel.setTitle("Transacoes Diarias");
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Transacoes");
        xAxis.setMin(0);
        xAxis.setMax(5);

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Data");
        yAxis.setMin(0);
        yAxis.setMax(500);
    }

    private void createHorizontalBarModel() {

        horizontalBarModel = new HorizontalBarChartModel();

        ChartSeries total = new ChartSeries();
        total.setLabel("Total");
        total.set(datada1, 490);
        total.set(datada2, 470);
        total.set(datada3, 398);
        total.set(datada4, 455);
        total.set(datada5, 500);

        ChartSeries pendentes = new ChartSeries();
        pendentes.setLabel("Pendentes");
        pendentes.set(datada1, 4);
        pendentes.set(datada2, 5);
        pendentes.set(datada3, 1);
        pendentes.set(datada4, 2);
        pendentes.set(datada5, 3);

        ChartSeries desfeitas = new ChartSeries();
        desfeitas.setLabel("Desfeitas");
        desfeitas.set(datada1, 2);
        desfeitas.set(datada2, 1);
        desfeitas.set(datada3, 2);
        desfeitas.set(datada4, 0);
        desfeitas.set(datada5, 2);

        ChartSeries negadas = new ChartSeries();
        negadas.setLabel("Negadas");
        negadas.set(datada1, 1);
        negadas.set(datada2, 0);
        negadas.set(datada3, 2);
        negadas.set(datada4, 0);
        negadas.set(datada5, 1);

        ChartSeries confirmadas = new ChartSeries();
        confirmadas.setLabel("Confirmadas");
        confirmadas.set(datada1, 486);
        confirmadas.set(datada2, 469);
        confirmadas.set(datada3, 395);
        confirmadas.set(datada4, 453);
        confirmadas.set(datada5, 499);

        horizontalBarModel.addSeries(total);
        horizontalBarModel.addSeries(pendentes);
        horizontalBarModel.addSeries(desfeitas);
        horizontalBarModel.addSeries(negadas);
        horizontalBarModel.addSeries(confirmadas);

        horizontalBarModel.setTitle("Transacoes diarias");
        horizontalBarModel.setLegendPosition("e");
        horizontalBarModel.setStacked(true);

        Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Data");
        yAxis.setMin(0);
        yAxis.setMax(5);

        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Transacoes");
        xAxis.setMin(0);
        yAxis.setMax(500);
    }

    private void createMultiAxisModel() {

        multiAxisModel = new LineChartModel();

        BarChartSeries series1 = new BarChartSeries();
        series1.setLabel("Boys");

        series1.set("Total", 120);
        series1.set("Pendentes", 100);
        series1.set("Desfeitas", 44);
        series1.set("Negadas", 150);
        series1.set("Confirmadas", 25);

        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Girls");
        series2.setXaxis(AxisType.X2);
        series2.setYaxis(AxisType.Y2);

        series2.set("A", 52);
        series2.set("B", 60);
        series2.set("C", 110);
        series2.set("D", 135);
        series2.set("E", 120);

        multiAxisModel.addSeries(series1);
        multiAxisModel.addSeries(series2);

        multiAxisModel.setTitle("Multi Axis Chart");
        multiAxisModel.setMouseoverHighlight(false);

        multiAxisModel.getAxes().put(AxisType.X, new CategoryAxis("Years"));
        multiAxisModel.getAxes().put(AxisType.X2, new CategoryAxis("Period"));

        Axis yAxis = multiAxisModel.getAxis(AxisType.Y);
        yAxis.setLabel("Birth");
        yAxis.setMin(0);
        yAxis.setMax(200);

        Axis y2Axis = new LinearAxis("Number");
        y2Axis.setMin(0);
        y2Axis.setMax(200);

        multiAxisModel.getAxes().put(AxisType.Y2, y2Axis);
    }

    private void createLineModels() {

        lineModel1 = initLinearModel();
        lineModel1.setTitle("Grafico Linear");
        lineModel1.setLegendPosition("e");
        Axis yAxis = lineModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(100);

        lineModel2 = initCategoryModel();
        lineModel2.setTitle("Category Chart");
        lineModel2.setLegendPosition("e");
        lineModel2.setShowPointLabels(true);
        lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Transacoes"));
        yAxis = lineModel2.getAxis(AxisType.Y);
        yAxis.setLabel("Datas");
        yAxis.setMin(0);
        yAxis.setMax(5);
    }

    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Hoje");

        series1.set(1, 20);
        series1.set(2, 13);
        series1.set(3, 34);
        series1.set(4, 65);
        series1.set(5, 86);

        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Ontem");

        series2.set(1, 6);
        series2.set(2, 3);
        series2.set(3, 2);
        series2.set(4, 7);
        series2.set(5, 9);

        LineChartSeries series3 = new LineChartSeries();
        series3.setLabel("Semana Passada");

        series3.set(1, 15);
        series3.set(2, 03);
        series3.set(3, 27);
        series3.set(4, 56);
        series3.set(5, 79);

        model.addSeries(series1);
        model.addSeries(series2);
        model.addSeries(series3);

        return model;
    }

    private LineChartModel initCategoryModel() {

        LineChartModel model = new LineChartModel();

        ChartSeries total = new ChartSeries();
        total.setLabel("Total");
        total.set(datada1, 490);
        total.set(datada2, 470);
        total.set(datada3, 398);
        total.set(datada4, 455);
        total.set(datada5, 500);

        ChartSeries pendentes = new ChartSeries();
        pendentes.setLabel("Pendentes");
        pendentes.set(datada1, 4);
        pendentes.set(datada2, 5);
        pendentes.set(datada3, 1);
        pendentes.set(datada4, 2);
        pendentes.set(datada5, 3);

        ChartSeries desfeitas = new ChartSeries();
        desfeitas.setLabel("Desfeitas");
        desfeitas.set(datada1, 2);
        desfeitas.set(datada2, 1);
        desfeitas.set(datada3, 2);
        desfeitas.set(datada4, 0);
        desfeitas.set(datada5, 2);

        ChartSeries negadas = new ChartSeries();
        negadas.setLabel("Negadas");
        negadas.set(datada1, 1);
        negadas.set(datada2, 0);
        negadas.set(datada3, 2);
        negadas.set(datada4, 0);
        negadas.set(datada5, 1);

        ChartSeries confirmadas = new ChartSeries();
        confirmadas.setLabel("Confirmadas");
        confirmadas.set(datada1, 486);
        confirmadas.set(datada2, 469);
        confirmadas.set(datada3, 395);
        confirmadas.set(datada4, 453);
        confirmadas.set(datada5, 499);

        model.addSeries(total);
        model.addSeries(pendentes);
        model.addSeries(desfeitas);
        model.addSeries(negadas);
        model.addSeries(confirmadas);

        return model;
    }

    private void createDonutModels() {
        donutModel1 = initDonutModel();
        donutModel1.setTitle("Donut Chart");
        donutModel1.setLegendPosition("w");

        donutModel2 = initDonutModel();
        donutModel2.setTitle("Over the last 3 days");
        donutModel2.setLegendPosition("e");
        donutModel2.setSliceMargin(5);
        donutModel2.setShowDataLabels(true);
        donutModel2.setDataFormat("value");
        donutModel2.setShadow(true);
    }

    private DonutChartModel initDonutModel() {

        DonutChartModel model = new DonutChartModel();

        Map<String, Number> circle1 = new LinkedHashMap<String, Number>();
        circle1.put("EMBRATEC", 150);
        circle1.put("BANCO TOPAZIO ATM", 110);
        circle1.put("TOPAZIO", 180);
        circle1.put("BANRISUL", 100);
        circle1.put("CARTAO FRETE", 140);
        circle1.put("TECBAN TOPAZIO", 130);
        circle1.put("BANPARA", 170);
        circle1.put("BANESE", 101);
        model.addCircle(circle1);

        Map<String, Number> circle2 = new LinkedHashMap<String, Number>();
        circle2.put("EMBRATEC", 250);
        circle2.put("BANCO TOPAZIO ATM", 210);
        circle2.put("TOPAZIO", 280);
        circle2.put("BANRISUL", 200);
        circle2.put("CARTAO FRETE", 240);
        circle2.put("TECBAN TOPAZIO", 230);
        circle2.put("BANPARA", 270);
        circle2.put("BANESE", 201);
        model.addCircle(circle2);

        Map<String, Number> circle3 = new LinkedHashMap<String, Number>();
        circle3.put("EMBRATEC", 350);
        circle3.put("BANCO TOPAZIO ATM", 310);
        circle3.put("TOPAZIO", 380);
        circle3.put("BANRISUL", 300);
        circle3.put("CARTAO FRETE", 340);
        circle3.put("TECBAN TOPAZIO", 330);
        circle3.put("BANPARA", 370);
        circle3.put("BANESE", 331);
        model.addCircle(circle3);

        return model;
    }

    public LineChartModel getMultiAxisModel() {
        if(multiAxisModel==null){
            init();
        }
        return multiAxisModel;
    }

    public void setMultiAxisModel(LineChartModel multiAxisModel) {
        if(multiAxisModel==null){
            init();
        }        
        this.multiAxisModel = multiAxisModel;
    }

    public LineChartModel getLineModel1() {
        if(lineModel1==null){
            init();
        }         
        return lineModel1;
    }

    public void setLineModel1(LineChartModel lineModel1) {
        this.lineModel1 = lineModel1;
    }

    public LineChartModel getLineModel2() {
        if(lineModel2==null){
            init();
        }         
        return lineModel2;
    }

    public void setLineModel2(LineChartModel lineModel2) {
        this.lineModel2 = lineModel2;
    }

    public DonutChartModel getDonutModel1() {
        if(donutModel1==null){
            init();
        }        
        return donutModel1;
    }

    public void setDonutModel1(DonutChartModel donutModel1) {
        this.donutModel1 = donutModel1;
    }

    public DonutChartModel getDonutModel2() {
        if(donutModel2==null){
            init();
        }         
        return donutModel2;
    }

    public void setDonutModel2(DonutChartModel donutModel2) {
        this.donutModel2 = donutModel2;
    }

    public BarChartModel getBarModel() {
        if(barModel==null){
            init();
        }        
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public HorizontalBarChartModel getHorizontalBarModel() {
        if(horizontalBarModel==null){
            init();
        }        
        return horizontalBarModel;
    }

    public void setHorizontalBarModel(HorizontalBarChartModel horizontalBarModel) {
        this.horizontalBarModel = horizontalBarModel;
    }

    public StreamedContent getChart() {
        if(chart==null){
            init();
        }         
        return chart;
    }

    public void setChart(StreamedContent chart) {
        this.chart = chart;
    }
}

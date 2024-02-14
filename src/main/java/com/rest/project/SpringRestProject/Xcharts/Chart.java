package com.rest.project.SpringRestProject.Xcharts;

import com.rest.project.SpringRestProject.models.Measurements;
import com.rest.project.SpringRestProject.services.MeasurementsService;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class Chart {

    private final MeasurementsService measurementsService;

    private int count = 0;

    public Chart(MeasurementsService measurementsService) {
        this.measurementsService = measurementsService;

    }

    public void createChart() {
        List<Measurements> measurementsList = measurementsService.findAll();
        List<Number> temperatures = new ArrayList<>();
        List<Date> timestamps = new ArrayList<>();

        for (Measurements measurements : measurementsList) {
            temperatures.add(measurements.getTemperature());
            timestamps.add(Date.from(measurements.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant()));
        }

        Collections.sort(timestamps);

        XYChart chart = new XYChartBuilder().width(1920).height(1080).title("Weather Chart").xAxisTitle("Time").yAxisTitle("Temperature").build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        XYSeries series = chart.addSeries("Temperature", timestamps, temperatures);
        series.setMarker(SeriesMarkers.CIRCLE);
        try {
            BitmapEncoder.saveBitmap(chart, "C:/Users/Fybea/Desktop/charts/charts" + count++, BitmapEncoder.BitmapFormat.PNG);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

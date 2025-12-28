package main;

import browser.NgordnetQuery;
import browser.Plotter;
import demo.DummyHistoryHandler;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends DummyHistoryHandler {
    private NGramMap map;

    public HistoryHandler(NGramMap map) {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for (String word : words) {
            labels.add(word);
            lts.add(map.weightHistory(word, startYear, endYear));
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);

        return Plotter.encodeChartAsString(chart);
    }
}

package PSO_FOR_TSP;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class PlotSolution {

    public static void plotSolution(int[] tour, TSPModel model) {
        double[] x = model.x;
        double[] y = model.y;

        // Tour'un sonuna başlangıç noktasını ekliyoruz
        int[] fullTour = new int[tour.length + 1];
        System.arraycopy(tour, 0, fullTour, 0, tour.length);
        fullTour[fullTour.length - 1] = tour[0]; // Başlangıç noktasını sona ekle

        // Grafiği çizmek için JFreeChart kullanıyoruz
        XYSeries series = new XYSeries("Tour Path", false, true);
        for (int i = 0; i < fullTour.length; i++) {
            series.add(x[fullTour[i]], y[fullTour[i]]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        // Line chart oluştur
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Tour Path", // Chart başlığı
                "X", // X ekseni etiketi
                "Y", // Y ekseni etiketi
                dataset, // Dataset
                PlotOrientation.VERTICAL,
                true, // Legend
                true, // Tooltips
                false // URLs
        );

        // Hem çizgileri hem noktaları göstermek için renderer kullanıyoruz
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, true); // Çizgiler ve noktalar açık

        // Nokta şekli olarak küçük bir daire kullanıyoruz
        Shape circle = new Ellipse2D.Double(-3, -3, 6, 6); // Çapı 6 olan bir daire (yarıçap 3)
        renderer.setSeriesShape(0, circle);

        // Çizgi ve nokta renklerini ayarlayalım
        renderer.setSeriesPaint(0, Color.BLUE);  // Çizgi rengi mavi
        renderer.setSeriesShapesVisible(0, true);  // Noktalar görünür olacak

        // Renderer'i plot'a ekleyelim
        plot.setRenderer(renderer);

        // Chart'ı bir panelde göster
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Örnek Model
        TSPModel model = new TSPModel();
        model.x = new double[]{57, 96, 79, 40, 93, 60, 58, 82, 30, 53, 86, 16, 60, 43, 69, 91, 23, 60, 86, 15};
        model.y = new double[]{86, 59, 53, 6, 74, 23, 70, 6, 35, 17, 48, 87, 37, 31, 45, 26, 17, 75, 65, 42};

        // Örnek tur
        int[] tour = {3, 15, 13, 7, 18, 1, 12, 20, 9, 14, 17, 4, 10, 6, 8, 16, 11, 2, 5, 19};

        plotSolution(tour, model);
    }
}

//class TSPModel {
//    public double[] x;
//    public double[] y;
//}
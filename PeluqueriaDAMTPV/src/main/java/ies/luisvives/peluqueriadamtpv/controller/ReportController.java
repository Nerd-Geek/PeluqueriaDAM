package ies.luisvives.peluqueriadamtpv.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    @FXML
    PieChart serviceHigher;
    @FXML
    BarChart genderFemale;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //genderFemale.getData().add();
        setServiceHigher();
        genderFemale();
    }

    public void setServiceHigher() {
        ObservableList<PieChart.Data> datosGraficoCircular = FXCollections.observableArrayList(
                new PieChart.Data("C", 0.1695f),
                new PieChart.Data("Java", 0.1256f),
                new PieChart.Data("Python", 0.1128f),
                new PieChart.Data("C++", 0.0694f),
                new PieChart.Data("C#", 0.0416f),
                new PieChart.Data("Visual Basic", 0.0397f),
                new PieChart.Data("JavaScript", 0.0214f),
                new PieChart.Data("PHP", 0.0209f),
                new PieChart.Data("R", 0.0199f),
                new PieChart.Data("SQL", 0.0157f));
        serviceHigher.setData(datosGraficoCircular);
        serviceHigher.setClockwise(false);
        serviceHigher.setTitle("Distribución lenguajes");
    }

    public void genderFemale() {
        XYChart.Series male = new XYChart.Series<>();
        XYChart.Series female = new XYChart.Series<>();
        genderFemale.setTitle("Géneros");
        male.setName("Masculino");
        male.getData().add(new XYChart.Data("",60));
        female.setName("Femenino");
        female.getData().add(new XYChart.Data("",80));
        genderFemale.getData().addAll(male, female);
        genderFemale.verticalGridLinesVisibleProperty();
    }
}

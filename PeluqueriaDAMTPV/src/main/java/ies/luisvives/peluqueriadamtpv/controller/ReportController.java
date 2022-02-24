package ies.luisvives.peluqueriadamtpv.controller;

import ies.luisvives.peluqueriadamtpv.model.Appointment;
import ies.luisvives.peluqueriadamtpv.model.Service;
import ies.luisvives.peluqueriadamtpv.restcontroller.APIRestConfig;
import ies.luisvives.peluqueriadamtpv.utils.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import retrofit2.Response;

import java.net.URL;
import java.util.List;
import java.util.Objects;
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

        ObservableList<Appointment> datosLista = FXCollections.observableArrayList();
        try {
            Response<List<Appointment>> serviceList = Objects.requireNonNull(APIRestConfig.getAppointmentsService().appointmentsGetAll().execute());
            if (serviceList.body() != null) {
                datosLista.addAll(serviceList.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int contadorMale = 0;
        for (Appointment male : datosLista) {
            if (male.getUser().getGender().toString().equals("Male")) {
                contadorMale++;
            }
        }

        int contadorFemale = 0;
        for (Appointment female : datosLista) {
            if (female.getUser().getGender().toString().equals("Female")) {
                contadorFemale++;
            }
        }
        XYChart.Series male = new XYChart.Series<>();
        XYChart.Series female = new XYChart.Series<>();
        genderFemale.setTitle(Util.getString("text.genders"));
        male.setName(Util.getString("text.male"));
        male.getData().add(new XYChart.Data("", contadorMale));
        female.setName(Util.getString("text.female"));
        female.getData().add(new XYChart.Data("", contadorFemale));
        genderFemale.getData().addAll(male, female);
        genderFemale.verticalGridLinesVisibleProperty();
    }

    public void genderFemale() {
        ObservableList<Service> datosLista = FXCollections.observableArrayList();
        try {
            Response<List<Service>> serviceList = Objects.requireNonNull(APIRestConfig.getServicesService().serviceGetAll().execute());
            if (serviceList.body() != null) {
                datosLista.addAll(serviceList.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int contadorPeloHombres = 0;
        for (Service pelo : datosLista) {
            if (pelo.getName().equals("Corte pelo Hombre")) {
                contadorPeloHombres++;
            }
        }
        int contadorNinos = 0;
        for (Service female : datosLista) {
            if (female.getName().equals("Corte Niños")) {
                contadorNinos++;
            }
        }
        int contadorMujer = 0;
        for (Service female : datosLista) {
            if (female.getName().equals("Corte pelo Mujer")) {
                contadorMujer++;
            }
        }
        int contadorEspecial = 0;
        for (Service female : datosLista) {
            if (female.getName().equals("Peinado Especial")) {
                contadorEspecial++;
            }
        }
        int contadorTinte = 0;
        for (Service female : datosLista) {
            if (female.getName().equals("Tinte Color Completo")) {
                contadorTinte++;
            }
        }
        int contadorMechas = 0;
        for (Service female : datosLista) {
            if (female.getName().equals("Mechas")) {
                contadorMechas++;
            }
        }
        int contadorCalifornianas = 0;
        for (Service female : datosLista) {
            if (female.getName().equals("Californianas")) {
                contadorCalifornianas++;
            }
        }
        int contadorDecoloracion = 0;
        for (Service female : datosLista) {
            if (female.getName().equals("Decoloración")) {
                contadorDecoloracion++;
            }
        }
        int contadorAlisado = 0;
        for (Service female : datosLista) {
            if (female.getName().equals("Alisado de Keratina")) {
                contadorAlisado++;
            }
        }

        ObservableList<PieChart.Data> datosGraficoCircular = FXCollections.observableArrayList(
                new PieChart.Data("Corte pelo Hombre", contadorPeloHombres),
                new PieChart.Data("Corte Niños", contadorNinos),
                new PieChart.Data("Corte pelo Mujer", contadorMujer),
                new PieChart.Data("Peinado Especial", contadorEspecial),
                new PieChart.Data("Tinte Color Completo", contadorTinte),
                new PieChart.Data("Mechas", contadorMechas),
                new PieChart.Data("Californianas", contadorCalifornianas),
                new PieChart.Data("Decoloración", contadorDecoloracion),
                new PieChart.Data("Alisado de Keratina", contadorAlisado));
        serviceHigher.setData(datosGraficoCircular);
        serviceHigher.setClockwise(false);
        serviceHigher.setTitle(Util.getString("text.programLanguageDistribution"));
    }

    public void cargarDatosPieChart(ObservableList<Appointment> listaDatos) {
    }
}

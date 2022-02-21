package ies.luisvives.peluqueriadamtpv.controller;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.Icon;

public class MainController {

    @FXML
    StackPane includedViewAppointments,includedViewUsers,includedViewServices, includedViewReports, includedViewSettings;
    @FXML
    ToggleButton mainViewSideMenuButtonAppointments,mainViewSideMenuButtonUsers,mainViewSideMenuButtonServices,mainViewSideMenuButtonReports,mainViewSideMenuButtonSettings;

    public void initialize() {
    }
    @FXML
    public void onMenuItemAppointmentsMouseClicked(){
        includedViewAppointments.setVisible(true);
        includedViewAppointments.setDisable(false);
        includedViewUsers.setVisible(false);
        includedViewUsers.setDisable(true);
        includedViewServices.setVisible(false);
        includedViewServices.setDisable(true);
        includedViewReports.setVisible(false);
        includedViewReports.setDisable(true);
        includedViewSettings.setVisible(false);
        includedViewSettings.setDisable(true);
    }
    @FXML
    public void onMenuItemUsersMouseClicked(){
        includedViewAppointments.setVisible(false);
        includedViewAppointments.setDisable(true);
        includedViewUsers.setVisible(true);
        includedViewUsers.setDisable(false);
        includedViewServices.setVisible(false);
        includedViewServices.setDisable(true);
        includedViewReports.setVisible(false);
        includedViewReports.setDisable(true);
        includedViewSettings.setVisible(false);
        includedViewSettings.setDisable(true);
    }
    @FXML
    public void onMenuItemServicesMouseClicked(){
        includedViewAppointments.setVisible(false);
        includedViewAppointments.setDisable(true);
        includedViewUsers.setVisible(false);
        includedViewUsers.setDisable(true);
        includedViewServices.setVisible(true);
        includedViewServices.setDisable(false);
        includedViewReports.setVisible(false);
        includedViewReports.setDisable(true);
        includedViewSettings.setVisible(false);
        includedViewSettings.setDisable(true);
    }
    @FXML
    public void onMenuItemReportsMouseClicked(){
        includedViewAppointments.setVisible(false);
        includedViewAppointments.setDisable(true);
        includedViewUsers.setVisible(false);
        includedViewUsers.setDisable(true);
        includedViewServices.setVisible(false);
        includedViewServices.setDisable(true);
        includedViewReports.setVisible(true);
        includedViewReports.setDisable(false);
        includedViewSettings.setVisible(false);
        includedViewSettings.setDisable(true);
    }
    @FXML
    public void onMenuItemSettingsMouseClicked(){
        includedViewAppointments.setVisible(false);
        includedViewAppointments.setDisable(true);
        includedViewUsers.setVisible(false);
        includedViewUsers.setDisable(true);
        includedViewServices.setVisible(false);
        includedViewServices.setDisable(true);
        includedViewReports.setVisible(false);
        includedViewReports.setDisable(true);
        includedViewSettings.setVisible(true);
        includedViewSettings.setDisable(false);
    }
}

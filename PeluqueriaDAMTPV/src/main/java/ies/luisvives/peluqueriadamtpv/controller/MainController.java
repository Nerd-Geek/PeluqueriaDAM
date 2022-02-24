package ies.luisvives.peluqueriadamtpv.controller;

import ies.luisvives.peluqueriadamtpv.model.UserConfiguration;
import ies.luisvives.peluqueriadamtpv.utils.Util;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class MainController {
    @FXML
    public SplitPane mainPane;
    @FXML
    StackPane includedViewAppointments, includedViewUsers, includedViewServices, includedViewReports, includedViewSettings;
    @FXML
    ToggleButton mainViewSideMenuButtonAppointments, mainViewSideMenuButtonUsers, mainViewSideMenuButtonServices, mainViewSideMenuButtonReports, mainViewSideMenuButtonSettings;

    @FXML
    TextField search_field;

    @FXML
    AppointmentController includedViewAppointmentsController;
    @FXML
    private Button search_button;

    public void initialize() {
        mainPane.getStylesheets().add(Objects.requireNonNull(this.getClass()
                .getResource(Util.PACKAGE_DIR + "themes/style_" +
                        UserConfiguration.getInstance().getActualTheme().toLowerCase()
                        + ".css")).toExternalForm());
    }

    @FXML
    public void onMenuItemAppointmentsMouseClicked() {
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
    public void onMenuItemUsersMouseClicked() {
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
    public void onMenuItemServicesMouseClicked() {
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
    public void onMenuItemReportsMouseClicked() {
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
    public void onMenuItemSettingsMouseClicked() {
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

    @FXML
    public void onSearchButtonClick() {
        includedViewAppointmentsController.setUserSearch(search_field.getText());
    }
}

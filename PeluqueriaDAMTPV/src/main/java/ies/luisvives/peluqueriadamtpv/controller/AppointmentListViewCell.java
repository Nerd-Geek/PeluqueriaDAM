package ies.luisvives.peluqueriadamtpv.controller;

import ies.luisvives.peluqueriadamtpv.model.AppointmentDTO;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
public class AppointmentListViewCell extends Pane {
    private AppointmentDTO appointmentDTO;
    private Label userLabel;
    private Label serviceLabel;
    private Label timeLabel;
    private Label dateLabel;
    private Button detailsButton;
    private Button modifyButton;
    private Button deleteButton;
    private HBox hbox;

    public AppointmentListViewCell(AppointmentDTO appointmentDTO) {
        this.appointmentDTO = appointmentDTO;
        this.hbox = new HBox();
        this.userLabel = new Label(appointmentDTO.getUser().getUsername());
        this.serviceLabel = new Label(appointmentDTO.getService().getName());
        this.timeLabel = new Label(appointmentDTO.getTime());
        this.dateLabel = new Label(appointmentDTO.getDate());
        this.detailsButton = new Button("");
        this.modifyButton = new Button("");
        this.deleteButton = new Button("");
        hbox.getChildren().add(userLabel);
        hbox.getChildren().add(serviceLabel);
        hbox.getChildren().add(timeLabel);
        hbox.getChildren().add(dateLabel);
        hbox.getChildren().add(detailsButton);
        hbox.getChildren().add(modifyButton);
        hbox.getChildren().add(deleteButton);
        this.getChildren().add(hbox);
    }
}

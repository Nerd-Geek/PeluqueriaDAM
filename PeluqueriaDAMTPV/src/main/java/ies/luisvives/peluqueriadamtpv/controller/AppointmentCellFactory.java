package ies.luisvives.peluqueriadamtpv.controller;

import ies.luisvives.peluqueriadamtpv.model.AppointmentDTO;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class AppointmentCellFactory implements Callback<ListView<AppointmentDTO>, ListCell<AppointmentDTO>> {
    @Override
    public ListCell<AppointmentDTO> call(ListView<AppointmentDTO> param) {
        return new ListCell<>() {
            @Override
            public void updateItem(AppointmentDTO appointmentDTO, boolean empty) {
                super.updateItem(appointmentDTO, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else if (appointmentDTO != null) {
                    setText(null);
                    setGraphic(new AppointmentListViewCell(appointmentDTO));
                } else {
                    setGraphic(null);
                    System.out.println("Null");
                }
            }
        };
    }
}

package ies.luisvives.peluqueriadamtpv.controller;

import ies.luisvives.peluqueriadamtpv.mapper.AppointmentListMapper;
import ies.luisvives.peluqueriadamtpv.model.AppointmentDTO;
import ies.luisvives.peluqueriadamtpv.model.AppointmentListDTO;
import ies.luisvives.peluqueriadamtpv.restcontroller.APIRestConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import retrofit2.Response;

import javax.security.auth.callback.Callback;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AppointmentTableController implements Initializable, Callback {
	@FXML
	TableView<AppointmentListDTO> list_appointments;
	private final TableColumn<AppointmentListDTO, String> userColumn;
	private final TableColumn<AppointmentListDTO, String> serviceColumn;
	private final TableColumn<AppointmentListDTO, String> timeColumn;
	private final TableColumn<AppointmentListDTO, String> dateColumn;
	@FXML
	private Button details_button;
	@FXML
	private Button modify_button;
	@FXML
	private Button delete_button;

	private String searchUser;


	public AppointmentTableController () {
		userColumn = new TableColumn<>("user");
		serviceColumn = new TableColumn<>("service");
		timeColumn = new TableColumn<>("time");
		dateColumn = new TableColumn<>("date");
		searchUser = "";
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		list_appointments.getColumns().addAll(userColumn, serviceColumn, timeColumn, dateColumn);
		onTableItemAppointments(null);
	}

	@FXML
	public void onTableItemAppointments (ActionEvent event) {
		try {
			Response<List<AppointmentDTO>> response = APIRestConfig.getAppointmentsService().appointmentGetAllWithUser_Username(searchUser).execute();
			if (response.body() != null) {
				AppointmentListMapper mapper = new AppointmentListMapper();
				ObservableList<AppointmentListDTO> appointments =
						FXCollections.observableArrayList(mapper.toList(response.body()));
				list_appointments.setItems(appointments);
				userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
				serviceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
				timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
				dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
//				userColumn.setSortType(TableColumn.SortType.DESCENDING);
//				serviceColumn.setSortType(TableColumn.SortType.DESCENDING);
//				timeColumn.setSortType(TableColumn.SortType.DESCENDING);
				list_appointments.setItems(appointments);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void deleteAppointment(ActionEvent e) {
		if (list_appointments.getSelectionModel().getSelectedCells().size() == 1) {
			String appointmentID = list_appointments.getItems().get(list_appointments.getSelectionModel().getFocusedIndex()).getId();
			try {
				APIRestConfig.getAppointmentsService().deleteAppointmentById(appointmentID).execute();
				list_appointments.getItems().remove(list_appointments.getSelectionModel().getFocusedIndex());
				System.out.println("delete done");
			} catch (IOException ioException) {
				System.err.println("Delete not done");
			}
		}
	}

	@FXML
	public void showAppointment () {
		if (list_appointments.getSelectionModel().getSelectedCells().size() == 1) {

		}
	}

	public void setSearchUser(String searchUser) {
		this.searchUser = searchUser;
	}

	public void refreshTable() {
		onTableItemAppointments(null);
	}
}

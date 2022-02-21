package ies.luisvives.peluqueriadamtpv.controller;

import ies.luisvives.peluqueriadamtpv.App;
import ies.luisvives.peluqueriadamtpv.model.AppointmentDTO;
import ies.luisvives.peluqueriadamtpv.restcontroller.APIRestConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.util.Callback;
import retrofit2.Response;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalDate;

public class AppointmentController implements Initializable {
	private Calendar calendar;

	@FXML
	private Label month_string;
	@FXML
	private Label year_string;
	@FXML
	private TextField userSearchField;
	@FXML
	private Button searchUserButton;
	@FXML
	private Button prev_month_button;
	@FXML
	private Button next_month_button;
	@FXML
	private Button prevServiceButton;
	@FXML
	private Button nextServiceButton;
	@FXML
	private Button createAppointmentButton;
	@FXML
	private Button day_button_0_0;
	@FXML
	private Button day_button_0_1;
	@FXML
	private Button day_button_0_2;
	@FXML
	private Button day_button_0_3;
	@FXML
	private Button day_button_0_4;
	@FXML
	private Button day_button_0_5;
	@FXML
	private Button day_button_0_6;
	@FXML
	private Button day_button_1_0;
	@FXML
	private Button day_button_1_1;
	@FXML
	private Button day_button_1_2;
	@FXML
	private Button day_button_1_3;
	@FXML
	private Button day_button_1_4;
	@FXML
	private Button day_button_1_5;
	@FXML
	private Button day_button_1_6;
	@FXML
	private Button day_button_2_0;
	@FXML
	private Button day_button_2_1;
	@FXML
	private Button day_button_2_2;
	@FXML
	private Button day_button_2_3;
	@FXML
	private Button day_button_2_4;
	@FXML
	private Button day_button_2_5;
	@FXML
	private Button day_button_2_6;
	@FXML
	private Button day_button_3_0;
	@FXML
	private Button day_button_3_1;
	@FXML
	private Button day_button_3_2;
	@FXML
	private Button day_button_3_3;
	@FXML
	private Button day_button_3_4;
	@FXML
	private Button day_button_3_5;
	@FXML
	private Button day_button_3_6;
	@FXML
	private Button day_button_4_0;
	@FXML
	private Button day_button_4_1;
	@FXML
	private Button day_button_4_2;
	@FXML
	private Button day_button_4_3;
	@FXML
	private Button day_button_4_4;
	@FXML
	private Button day_button_4_5;
	@FXML
	private Button day_button_4_6;
	@FXML
	private Button day_button_5_0;
	@FXML
	private Button day_button_5_1;
	@FXML
	private Button day_button_5_2;
	@FXML
	private Button day_button_5_3;
	@FXML
	private Button day_button_5_4;
	@FXML
	private Button day_button_5_5;
	@FXML
	private Button day_button_5_6;

	private List<List<Button>> gridButtons;

	public AppointmentController() {
		calendar = Calendar.getInstance();
		gridButtons = new ArrayList<>();
	}
	private void setButtonNamesForMonthYear(Calendar calendar) {
		gridButtons.forEach(l -> l.forEach(b -> {
			b.setText("");
			b.setDisable(true);
		}));
		int firstDayIndex = calculateFirstDayPosition(calendar);
		int[] lastPosition = calculateLastDayPosition(calendar, firstDayIndex);
		int lastDayIndex = 6;
//		int lastRow = 4;
//		if (firstDayIndex == 5 && calendar.getActualMaximum(Calendar.DATE) != 30 || firstDayIndex == 6)
//			lastRow = 5;
		int day = 1;
		for (int i = 0; i <=lastPosition[1]; i++) {
			if (i != 0) firstDayIndex = 0;
			if (i == lastPosition[1]) {
				lastDayIndex = lastPosition[0];
			}
			for (int j = firstDayIndex; j <= lastDayIndex; j++) {
				gridButtons.get(i).get(j).setText(day + "");
				if (
						(day >= calendar.get(Calendar.DAY_OF_MONTH)
							&& calendar.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)
							&& calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)
						)
						|| calendar.getTime().after(Calendar.getInstance().getTime()))
					gridButtons.get(i).get(j).setDisable(false);
				day++;
			}
		}
	}

	private int[] calculateLastDayPosition(Calendar calendar, int firstDayIndex) {
		Calendar lastDayOfMonth = Calendar.getInstance();
		lastDayOfMonth.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getActualMaximum(Calendar.DATE));
		if (lastDayOfMonth.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return new int[]{ 6, firstDayIndex + calendar.getActualMaximum(Calendar.DATE)/7};
		}
		else {
			return new int[]{lastDayOfMonth.get(Calendar.DAY_OF_WEEK) - 2, firstDayIndex + calendar.getActualMaximum(Calendar.DATE)/7};
		}
	}

	private int calculateFirstDayPosition(Calendar calendar) {
		Calendar firstDayOfMonth = Calendar.getInstance();
		firstDayOfMonth.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		if (firstDayOfMonth.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) return 6;
		else return firstDayOfMonth.get(Calendar.DAY_OF_WEEK) - 2;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		gridButtons.add(List.of(day_button_0_0, day_button_0_1, day_button_0_2, day_button_0_3, day_button_0_4, day_button_0_5, day_button_0_6));
		gridButtons.add(List.of(day_button_1_0, day_button_1_1, day_button_1_2, day_button_1_3, day_button_1_4, day_button_1_5, day_button_1_6));
		gridButtons.add(List.of(day_button_2_0, day_button_2_1, day_button_2_2, day_button_2_3, day_button_2_4, day_button_2_5, day_button_2_6));
		gridButtons.add(List.of(day_button_3_0, day_button_3_1, day_button_3_2, day_button_3_3, day_button_3_4, day_button_3_5, day_button_3_6));
		gridButtons.add(List.of(day_button_4_0, day_button_4_1, day_button_4_2, day_button_4_3, day_button_4_4, day_button_4_5, day_button_4_6));
		gridButtons.add(List.of(day_button_5_0, day_button_5_1, day_button_5_2, day_button_5_3, day_button_5_4, day_button_5_5, day_button_5_6));
		setButtonNamesForMonthYear(calendar);
		updateMonthYearLabel();
	}

	private void updateMonthYearLabel() {
		DateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
		month_string.setText(monthFormat.format(calendar.getTime()));
		year_string.setText(calendar.get(Calendar.YEAR) + "");
	}

	@FXML
	public void nextMonthButtonAction() {
		if (calendar.get(Calendar.MONTH)== Calendar.DECEMBER)
			calendar.set(calendar.get(Calendar.YEAR) + 1, Calendar.JANUARY, calendar.get(Calendar.DAY_OF_MONTH));
		else
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
		setButtonNamesForMonthYear(calendar);
		updateMonthYearLabel();
	}

	@FXML
	public void prevMonthButtonAction() {
		if (calendar.get(Calendar.MONTH)== Calendar.JANUARY)
			calendar.set(calendar.get(Calendar.YEAR) - 1, Calendar.DECEMBER, calendar.get(Calendar.DAY_OF_MONTH));
		else
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) - 1, calendar.get(Calendar.DAY_OF_MONTH));
		setButtonNamesForMonthYear(calendar);
		updateMonthYearLabel();
	}

	@FXML
	public void onCalendarDayAction (ActionEvent event) {
		System.out.println("Button pressed");
		LocalDate date = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, Integer.parseInt(((Button)event.getSource()).getText()));
		System.out.println("REST petition with date " + date);
//		APIRestConfig.getAppointmentsService().appointmentGetAllWithDate(Date.from(Instant.from(date)));
		try {
			Response<List<AppointmentDTO>> response = APIRestConfig.getAppointmentsService().appointmentsGetAll().execute();
			if (response.body() != null) {
				listAppointmentDTO = FXCollections.observableList(response.body());
				list_view_appointments.setItems(listAppointmentDTO);
				list_view_appointments.setCellFactory(new AppointmentCellFactory());
			}else {
				System.out.println("F");
			}
		} catch (Exception e) {
			System.err.println("Cagaste");
			e.printStackTrace();
		}
	}
}

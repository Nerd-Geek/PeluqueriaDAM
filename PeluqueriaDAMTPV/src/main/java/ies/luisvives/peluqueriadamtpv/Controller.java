package ies.luisvives.peluqueriadamtpv;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
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
	private ListView<?> listAppointmentView;
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

	private List<List<Button>> gridButtons;

	public Controller () {
		calendar = Calendar.getInstance();
		gridButtons = new ArrayList<>();
	}
	private void setButtonNamesForMonthYear(Calendar calendar) {
		gridButtons.forEach(l -> l.forEach(b -> b.setText("")));
		int firstDayIndex = calculateFirstDayPosition(calendar);
		int lastDayIndex = 6;
		int day = 1;
		for (int i = 0; i <=4; i++) {
			if (i != 0) firstDayIndex = 0;
			if (i == 4) {
				lastDayIndex = calculateLastDayPosition(calendar);
				System.out.println(lastDayIndex);
			}
			for (int j = firstDayIndex; j <= lastDayIndex; j++) {
				gridButtons.get(i).get(j).setText(day + "");
				day++;
			}
		}
	}

	private int calculateLastDayPosition(Calendar calendar) {
		Calendar lastDayOfMonth = Calendar.getInstance();
		lastDayOfMonth.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getActualMaximum(Calendar.DATE));
		if (lastDayOfMonth.get(Calendar.DAY_OF_WEEK) == 1) return 6;
		else return lastDayOfMonth.get(Calendar.DAY_OF_WEEK) - 2;
	}

	private int calculateFirstDayPosition(Calendar calendar) {
		Calendar firstDayOfMonth = Calendar.getInstance();
		firstDayOfMonth.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		if (firstDayOfMonth.get(Calendar.DAY_OF_WEEK) == 1) return 6;
		else return firstDayOfMonth.get(Calendar.DAY_OF_WEEK) - 2;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		gridButtons.add(List.of(day_button_0_0, day_button_0_1, day_button_0_2, day_button_0_3, day_button_0_4,day_button_0_5, day_button_0_6));
		gridButtons.add(List.of(day_button_1_0, day_button_1_1, day_button_1_2, day_button_1_3, day_button_1_4,day_button_1_5, day_button_1_6));
		gridButtons.add(List.of(day_button_2_0, day_button_2_1, day_button_2_2, day_button_2_3, day_button_2_4,day_button_2_5, day_button_2_6));
		gridButtons.add(List.of(day_button_3_0, day_button_3_1, day_button_3_2, day_button_3_3, day_button_3_4,day_button_3_5, day_button_3_6));
		gridButtons.add(List.of(day_button_4_0, day_button_4_1, day_button_4_2, day_button_4_3, day_button_4_4,day_button_4_5, day_button_4_6));
		setButtonNamesForMonthYear(Calendar.getInstance());
		month_string.setText(calendar.get(Calendar.MONTH) + "");
		year_string.setText(calendar.get(Calendar.YEAR) + "");
	}

	@FXML
	public void nextMonthButtonAction() {
		if (calendar.get(Calendar.MONTH)==11)
			calendar.set(calendar.get(Calendar.YEAR) + 1, 0, calendar.get(Calendar.DAY_OF_MONTH));
		else
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
		setButtonNamesForMonthYear(calendar);
		month_string.setText(calendar.get(Calendar.MONTH) + 1 + "");
		year_string.setText(calendar.get(Calendar.YEAR) + "");
	}

	@FXML
	public void prevMonthButtonAction() {
		if (calendar.get(Calendar.MONTH)==0)
			calendar.set(calendar.get(Calendar.YEAR) - 1, 11, calendar.get(Calendar.DAY_OF_MONTH));
		else
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) - 1, calendar.get(Calendar.DAY_OF_MONTH));
		setButtonNamesForMonthYear(calendar);
		month_string.setText(calendar.get(Calendar.MONTH) + 1 + "");
		year_string.setText(calendar.get(Calendar.YEAR) + "");
	}
}

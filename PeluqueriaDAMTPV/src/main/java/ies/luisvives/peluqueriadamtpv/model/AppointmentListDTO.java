package ies.luisvives.peluqueriadamtpv.model;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.value.ObservableStringValue;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppointmentListDTO {
	private String id;
	private String user;
	private String service;
	private String time;
	private String date;
}

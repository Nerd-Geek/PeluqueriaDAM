package ies.luisvives.peluqueriadamtpv.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppointmentListDTO {
	private String username;
	private String service;
	private String time;
	private String date;
}

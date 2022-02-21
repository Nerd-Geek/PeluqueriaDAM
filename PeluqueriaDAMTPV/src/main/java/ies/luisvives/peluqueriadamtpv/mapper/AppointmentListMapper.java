package ies.luisvives.peluqueriadamtpv.mapper;

import ies.luisvives.peluqueriadamtpv.model.AppointmentDTO;
import ies.luisvives.peluqueriadamtpv.model.AppointmentListDTO;

import java.util.List;
import java.util.stream.Collectors;

public class AppointmentListMapper {
	public AppointmentListDTO toListItem(AppointmentDTO appointmentDTO) {
		return new AppointmentListDTO(
				appointmentDTO.getUser().getUsername()
				, appointmentDTO.getService().getName()
				, appointmentDTO.getTime()
				, appointmentDTO.getDate());
	}

	public List<AppointmentListDTO> toList(List<AppointmentDTO> list) {
		return list.stream().map(this::toListItem).collect(Collectors.toList());
	}
}

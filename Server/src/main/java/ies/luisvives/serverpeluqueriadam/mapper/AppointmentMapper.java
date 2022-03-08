package ies.luisvives.serverpeluqueriadam.mapper;

import ies.luisvives.serverpeluqueriadam.dto.appointment.AppointmentDTO;
import ies.luisvives.serverpeluqueriadam.dto.appointment.UserlessAppointmentDTO;
import ies.luisvives.serverpeluqueriadam.model.Appointment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AppointmentMapper {
    private final ModelMapper modelMapper;

    public AppointmentDTO toDTO(Appointment appointment) {
        return modelMapper.map(appointment, AppointmentDTO.class);
    }

    public Appointment fromDTO(AppointmentDTO appointmentDTO) {
        return modelMapper.map(appointmentDTO, Appointment.class);
    }

    public List<AppointmentDTO> toDTO(List<Appointment> appointments) {
        return appointments.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public UserlessAppointmentDTO toUserlessDTO(Appointment appointment) {
        return modelMapper.map(appointment, UserlessAppointmentDTO.class);
    }

    public Appointment fromUserlessDTO (UserlessAppointmentDTO userlessAppointmentDTO) {
        return modelMapper.map(userlessAppointmentDTO, Appointment.class);
    }

    public List<UserlessAppointmentDTO> toUserlessDTO(List<Appointment> appointments) {
        return appointments.stream().map(this::toUserlessDTO).collect(Collectors.toList());
    }

    public List<Appointment> fromUserlessDTO(List<UserlessAppointmentDTO> userlessAppointments) {
        return userlessAppointments.stream().map(this::fromUserlessDTO).collect(Collectors.toList());
    }
}
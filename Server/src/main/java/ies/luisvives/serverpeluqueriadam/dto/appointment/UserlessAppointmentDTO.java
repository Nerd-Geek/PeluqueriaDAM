package ies.luisvives.serverpeluqueriadam.dto.appointment;

import ies.luisvives.serverpeluqueriadam.dto.service.ServiceDTO;
import ies.luisvives.serverpeluqueriadam.dto.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class UserlessAppointmentDTO {
    private String id;
    @NotNull(message = "La fecha no puede ser nula")
    private LocalDate date;
    @NotNull(message = "El tiempo no puede ser nulo")
    private LocalTime time;
    @NotNull(message = "El servicio no puede ser nulo")
    private ServiceDTO service;
}

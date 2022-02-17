package ies.luisvives.serverpeluqueriadam.dto.appointment;

<<<<<<< feature/TPV
import ies.luisvives.serverpeluqueriadam.dto.user.UserDTO;
=======
import ies.luisvives.serverpeluqueriadam.model.Service;
>>>>>>> Modificaciones
import ies.luisvives.serverpeluqueriadam.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AppointmentDTO {
    private String id;
    private LocalDate date;
    private LocalTime time;
    private User user;
    private Service service;
}
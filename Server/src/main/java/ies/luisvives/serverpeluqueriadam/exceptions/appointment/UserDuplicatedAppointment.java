package ies.luisvives.serverpeluqueriadam.exceptions.appointment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserDuplicatedAppointment extends RuntimeException {
    public UserDuplicatedAppointment(String id, @NotNull(message = "La fecha no puede ser nula") LocalDate date, @NotNull(message = "El tiempo no puede ser nulo") LocalTime time) {
        super("User with id "+ id + " already has an appointment at datetime" + date + "" + time);
    }
}

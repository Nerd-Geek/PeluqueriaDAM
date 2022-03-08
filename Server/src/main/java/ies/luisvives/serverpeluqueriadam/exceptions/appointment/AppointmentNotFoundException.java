package ies.luisvives.serverpeluqueriadam.exceptions.appointment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AppointmentNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 86546786467580532L;

    public AppointmentNotFoundException(String id) {
        super("No se ha encontrado la cita con la ID: " + id);
    }
}
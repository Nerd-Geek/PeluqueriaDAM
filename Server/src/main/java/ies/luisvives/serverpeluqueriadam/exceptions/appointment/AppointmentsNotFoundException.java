package ies.luisvives.serverpeluqueriadam.exceptions.appointment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AppointmentsNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 86546786467580532L;
    public AppointmentsNotFoundException() {
        super("La lista de citas está vacía o no existe");
    }
}
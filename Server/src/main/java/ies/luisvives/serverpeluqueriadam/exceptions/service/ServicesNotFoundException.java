package ies.luisvives.serverpeluqueriadam.exceptions.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServicesNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 86546786467580532L;
    public ServicesNotFoundException() {
        super("La lista de logins está vacía o no existe");
    }
}

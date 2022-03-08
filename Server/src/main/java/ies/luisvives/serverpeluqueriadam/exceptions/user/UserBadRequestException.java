package ies.luisvives.serverpeluqueriadam.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserBadRequestException extends RuntimeException {
    private static final long serialVersionUID = 86546786467580532L;

    public UserBadRequestException(String campo, String error) {
        super("Existe un error en el campo: " + campo + " Error: " + error);
    }
}

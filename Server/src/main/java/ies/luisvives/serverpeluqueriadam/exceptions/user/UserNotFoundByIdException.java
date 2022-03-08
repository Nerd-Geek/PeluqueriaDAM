package ies.luisvives.serverpeluqueriadam.exceptions.user;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundByIdException extends RuntimeException {
    private static final long serialVersionUID = 86546786467580532L;

    public UserNotFoundByIdException(String id) {
        super("No se ha encontrado el user con la ID: " + id);
    }
}

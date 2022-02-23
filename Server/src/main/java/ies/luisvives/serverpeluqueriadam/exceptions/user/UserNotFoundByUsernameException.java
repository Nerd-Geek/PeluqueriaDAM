package ies.luisvives.serverpeluqueriadam.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundByUsernameException extends RuntimeException{
    private static final long serialVersionUID = 86546786467580533L;

    public UserNotFoundByUsernameException(String username) {
        super("No se ha encontrado el user con el username: " + username);
    }
}

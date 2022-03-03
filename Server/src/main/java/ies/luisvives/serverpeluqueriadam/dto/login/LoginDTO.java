package ies.luisvives.serverpeluqueriadam.dto.login;

import ies.luisvives.serverpeluqueriadam.dto.user.LoginUserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class LoginDTO {
    private String id;
    @NotBlank(message = "El token no puede ser vacío")
    private String token;
    @NotNull(message = "La fecha no puede ser nula")
    private Date instance;
    @NotNull(message = "El usuario no puede ser nulo")
    private LoginUserDTO user;
}
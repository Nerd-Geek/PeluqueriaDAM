package ies.luisvives.serverpeluqueriadam.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginUserDTO {
    @Email(regexp = ".*@.*\\..*", message = "Email debe ser válido")
    private String email;
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username;
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
}

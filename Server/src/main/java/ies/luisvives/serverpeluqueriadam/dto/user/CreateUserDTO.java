package ies.luisvives.serverpeluqueriadam.dto.user;

import ies.luisvives.serverpeluqueriadam.model.UserGender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateUserDTO {
    @NotBlank(message = "El id no puede estar vacio")
    private String id;
    private String image;
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username;
    @NotBlank(message = "La password no puede estar vacía")
    private String password;
    @NotBlank(message = "La confirmación de contraseña no puede estar vacia")
    private String passwordConfirm;
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;
    @NotBlank(message = "El apellido no puede estar vacío")
    private String surname;
    @Size(min = 9, max = 15)
    @NotBlank(message = "El número de teléfono no puede estar vacío")
    private String phoneNumber;
    @Email(regexp = ".*@.*\\..*", message = "Email debe ser válido")
    private String email;
    @NotNull(message = "El género no puede estar nulo")
    private UserGender gender;
}

package ies.luisvives.serverpeluqueriadam.dto.user;

import ies.luisvives.serverpeluqueriadam.model.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private String id;
    private String image;

    private String username;
    // Comento la contraseña porque no quiero mostrarla o sí si queremos recuperarla o sobreescribirla
    //private String password;
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;
    @NotBlank(message = "El apellido no puede estar vacío")
    private String surname;
    @NotBlank(message = "El número de teléfono no puede estar vacío")
    private String phoneNumber;
    @Email(regexp = ".*@.*\\..*", message = "Email debe ser válido")
    private String email;
    @NotNull(message = "El género no puede ser nulo")
    private UserGender gender;
    @NotNull(message = "Los roles no pueden ser nulos")
    private Set<UserRole> userRoles;
    // TODO: RECUSRSIVIDAD COMENTARLA
    //private Set<Login> logins;
    //private Set<Appointment> appointments;
}

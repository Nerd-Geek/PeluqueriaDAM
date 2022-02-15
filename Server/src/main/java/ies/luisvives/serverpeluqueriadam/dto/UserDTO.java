package ies.luisvives.serverpeluqueriadam.dto;

import ies.luisvives.serverpeluqueriadam.model.Appointment;
import ies.luisvives.serverpeluqueriadam.model.Login;
import ies.luisvives.serverpeluqueriadam.model.User;
import ies.luisvives.serverpeluqueriadam.model.UserGender;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private String id;
    private String image;
    private boolean superUser;
    private String username;
    // Comento la contraseña porque no quiero mostrarla o sí si queremos recuperarla o sobreescribirla
    //private String password;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private Set<String> genders;
    // TODO: RECUSRSIVIDAD COMENTARLA
    //private Set<Login> logins;
    //private Set<Appointment> appointments;
}

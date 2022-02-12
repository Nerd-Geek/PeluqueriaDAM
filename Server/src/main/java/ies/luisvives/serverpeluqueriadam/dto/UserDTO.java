package ies.luisvives.serverpeluqueriadam.dto;

import ies.luisvives.serverpeluqueriadam.model.Appointment;
import ies.luisvives.serverpeluqueriadam.model.Login;
import ies.luisvives.serverpeluqueriadam.model.User;
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
    private String password;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private User.Gender gender;
    // TODO: RECUSRSIVIDAD COMENTARLA
    //private Set<Login> logins;
    //private Set<Appointment> appointments;
}

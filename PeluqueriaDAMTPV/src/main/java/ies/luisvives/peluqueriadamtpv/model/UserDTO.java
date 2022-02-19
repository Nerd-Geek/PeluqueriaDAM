package ies.luisvives.peluqueriadamtpv.model;

import lombok.*;

@Data
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
    private UserGender gender;
    // TODO: RECUSRSIVIDAD COMENTARLA
    //private Set<Login> logins;
    //private Set<Appointment> appointments;
}

package ies.luisvives.serverpeluqueriadam.dto.user;

import ies.luisvives.serverpeluqueriadam.model.UserGender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDTO {
    private String id;
    private String image;
    private boolean superUser;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private UserGender genders;
}

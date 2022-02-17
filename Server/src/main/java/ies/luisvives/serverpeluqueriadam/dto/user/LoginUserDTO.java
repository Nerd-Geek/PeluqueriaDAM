package ies.luisvives.serverpeluqueriadam.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDTO {
    private String email;
    private String username;
    private String password;
}

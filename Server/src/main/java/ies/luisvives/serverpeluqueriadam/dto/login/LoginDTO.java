package ies.luisvives.serverpeluqueriadam.dto.login;

import ies.luisvives.serverpeluqueriadam.dto.user.LoginUserDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LoginDTO {
    private String id;
    private String token;
    private Date instance;
    private LoginUserDTO user;
}
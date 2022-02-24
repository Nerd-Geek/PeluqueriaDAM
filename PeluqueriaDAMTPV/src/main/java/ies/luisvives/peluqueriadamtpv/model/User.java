package ies.luisvives.peluqueriadamtpv.model;

import lombok.*;

@Data
public class User {
    private String id;
    private String image;
    private boolean superUser;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private UserGender gender;
}
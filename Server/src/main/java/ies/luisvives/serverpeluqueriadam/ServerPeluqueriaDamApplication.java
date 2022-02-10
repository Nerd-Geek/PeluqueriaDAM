package ies.luisvives.serverpeluqueriadam;

import ies.luisvives.serverpeluqueriadam.model.User;
import ies.luisvives.serverpeluqueriadam.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class ServerPeluqueriaDamApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerPeluqueriaDamApplication.class, args);
    }

    @Bean
    public CommandLineRunner initUsers(UserRepository repository) {
        return args -> {
            repository.save(
                    User.builder()
                            .id(UUID.randomUUID().toString())
                            .superUser(true)
                            .name("poro")
                            .surname("freljorld")
                            .username("peludito150")
                            .password("braumILY")
                            .email("porofernandez@freljorld.com")
                            .phoneNumber("234567890")
                            .gender(User.Gender.Male)
                    .build());
        };
    }



}

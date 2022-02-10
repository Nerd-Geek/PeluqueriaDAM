package ies.luisvives.serverpeluqueriadam;

import ies.luisvives.serverpeluqueriadam.model.Appointment;
import ies.luisvives.serverpeluqueriadam.model.Login;
import ies.luisvives.serverpeluqueriadam.model.Service;
import ies.luisvives.serverpeluqueriadam.model.User;
import ies.luisvives.serverpeluqueriadam.repository.AppointmentRepository;
import ies.luisvives.serverpeluqueriadam.repository.LoginRepository;
import ies.luisvives.serverpeluqueriadam.repository.ServiceRepository;
import ies.luisvives.serverpeluqueriadam.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@SpringBootApplication
public class ServerPeluqueriaDamApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerPeluqueriaDamApplication.class, args);
    }
    static User user = User.builder()
            .id(UUID.randomUUID().toString())
            .superUser(true)
            .name("poro")
            .surname("freljorld")
            .username("peludito150")
            .password("braumILY")
            .email("porofernandez@freljorld.com")
            .phoneNumber("234567890")
            .gender(User.Gender.Male)
            .build();

    static Appointment appointment = Appointment.builder()
            .id(UUID.randomUUID().toString())
            .date(LocalDate.now())
            .time(LocalTime.now())
            .user(user)
            .build();

    static Service service = Service.builder()
            .id(UUID.randomUUID().toString())
            .name("Corte pelo")
            .image("/a")
            .description("Este es un corte de pelo para gnomos")
            .price(100.00)
            .stock(4)
            .build();

    static Login login = Login.builder()
            .id(UUID.randomUUID().toString())
            .token("123213412")
            .image("a")
            .instance(Date.from(Instant.now()))
            .user(user)
            .build();
    @Bean
    public CommandLineRunner initUsers(UserRepository repository) {
        return args -> {
            repository.save(
                    user);
        };
    }

    @Bean
    public CommandLineRunner initAppointment(AppointmentRepository repository) {
        return args -> {
            repository.save(
                    appointment);
        };
    }

    @Bean
    public CommandLineRunner initService(ServiceRepository repository) {
        return args -> {
            repository.save(
                    service);
        };
    }

    @Bean
    public CommandLineRunner initLogin(LoginRepository repository) {
        return args -> {
            repository.save(login);
        };
    }
}

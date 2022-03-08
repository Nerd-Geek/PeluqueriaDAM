package ies.luisvives.serverpeluqueriadam.repositories.user;

import ies.luisvives.serverpeluqueriadam.model.*;
import ies.luisvives.serverpeluqueriadam.repository.AppointmentRepository;
import ies.luisvives.serverpeluqueriadam.repository.ServiceRepository;
import ies.luisvives.serverpeluqueriadam.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserRepositoryMockTest {

    @MockBean
    private ServiceRepository serviceRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AppointmentRepository appointmentRepository;

    private static User user = User.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac1200015")
            .username("nombre usuario")
            .name("nombre")
            .surname("apellido")
            .phoneNumber("912345678")
            .gender(UserGender.Male)
            .email("adsada@sdasdd.com")
            .password("asd")
            .appointments(new HashSet<>())
            .logins(new HashSet<>())
            .roles(Set.of(UserRole.USER, UserRole.ADMIN))
            .build();

    private static Login login = Login.builder()
            .id("233149e4-b6f3-4692-ac71-2e8123bc2416")
            .user(user)
            .instant(Date.from(Instant.now()))
            .token("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjMTMzNGQ1Ny0" +
                    "xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NjY4OTIsI" +
                    "mV4cCI6MTY0NjY1MzI5MiwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE" +
                    "1JTiJ9.8LxNLPBZF5xxmpVF3GRNyFOQCVnSGQlnOZiEW6BNZjb5VkvELVJR30Rc4BK4W" +
                    "ddlAivBy7dUEbArgy-EYgTW0w")
            .build();

    private static Service service = Service.builder()
            .id("7dafe5fd-976b-450a-9bab-17ab450a4f17")
            .name("nombre")
            .stock(5)
            .price(2.5)
            .build();


    private static Appointment appointment1 = Appointment.builder()
            .id("0fc7d018-9d32-11ec-b909-0242ac120018")
            .service(service)
            .user(user)
            .date(LocalDate.of(2022, 3, 15))
            .time(LocalTime.of(9, 0, 0))
            .build();

    private static Appointment appointment2 = Appointment.builder()
            .id("0fc7d018-9d32-11ec-b909-0242ac120019")
            .service(service)
            .user(user)
            .date(LocalDate.of(2022, 3, 16))
            .time(LocalTime.of(10, 0, 0))
            .build();

    private static Appointment appointment3 = Appointment.builder()
            .id("0fc7d018-9d32-11ec-b909-0242ac120020")
            .service(service)
            .user(user)
            .date(LocalDate.of(2022, 3, 17))
            .time(LocalTime.of(11, 0, 0))
            .build();

    @Test
    void findAll() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        List<User> users = userRepository.findAll();
        assertAll(
                () -> assertEquals(users.size(), 1)
        );
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    void findById() {
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertEquals(userRepository.findById(user.getId()).orElse(null), user);
        Mockito.verify(userRepository, Mockito.times(1)).findById(user.getId());
    }

    @Test
    void save() {
        Mockito.when(userRepository.save(user)).thenReturn(user);

        assertEquals(userRepository.save(user), user);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void delete() {
        Mockito.doNothing().when(userRepository).delete(user);
        userRepository.delete(user);
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }
}

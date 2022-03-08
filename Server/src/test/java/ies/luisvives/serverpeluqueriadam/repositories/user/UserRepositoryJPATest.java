package ies.luisvives.serverpeluqueriadam.repositories.user;

import ies.luisvives.serverpeluqueriadam.model.*;
import ies.luisvives.serverpeluqueriadam.repository.AppointmentRepository;
import ies.luisvives.serverpeluqueriadam.repository.LoginRepository;
import ies.luisvives.serverpeluqueriadam.repository.ServiceRepository;
import ies.luisvives.serverpeluqueriadam.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TypeExcludeFilters(value= DataJpaTypeExcludeFilter.class)
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@ImportAutoConfiguration
public class UserRepositoryJPATest {

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

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        user.setAppointments(Set.of(appointment1, appointment2, appointment3));
        user.setLogins(Set.of(login));
        service.setAppointments(Set.of(appointment1, appointment2, appointment3));
        entityManager.persist(user);
        entityManager.persist(login);
        entityManager.persist(service);
        entityManager.persist(appointment1);
        entityManager.persist(appointment2);
        entityManager.persist(appointment3);
        entityManager.flush();
    }

    @Test
    void getAll() {
        assertTrue(appointmentRepository.findAll().size() > 0);
    }

    @Test
    void getById() {

        User theUser = userRepository.findById(user.getId()).orElse(null);
        assertAll(
                () -> assertEquals(user.getId(), theUser.getId()),
                () -> assertEquals(user.getUsername(), theUser.getUsername()),
                () -> assertEquals(user.getPassword(), theUser.getPassword()),
                () -> assertEquals(user.getName(), theUser.getName()),
                () -> assertEquals(user.getEmail(), theUser.getEmail()),
                () -> assertEquals(user.getImage(), theUser.getImage()),
                () -> assertEquals(user.getSurname(), theUser.getSurname()),
                () -> assertEquals(user.getGender(), theUser.getGender()),
                () -> assertEquals(user.getPhoneNumber(), theUser.getPhoneNumber()),
                () -> assertEquals(user.getRoles(), theUser.getRoles()),
                () -> assertEquals(user.getAppointments().size(), theUser.getAppointments().size()),
                () -> assertEquals(user.getLogins().size(), theUser.getLogins().size())
        );
    }

    @Test
    void save() {
        User newUser = User.builder()
                .id(UUID.randomUUID().toString())
                .username("poquito")
                .password("$2a$12$MvKeyxrlM0PxpT8egBnq1O/L/rY36QUWyhYDG7JINbziLpmYKmgDW")
                .name("poco")
                .email("poco@poco.com")
                .phoneNumber("609580881")
                .image(null)
                .gender(UserGender.Male)
                .surname("poquin")
                .roles(Set.of(UserRole.USER, UserRole.ADMIN))
                .appointments(new HashSet<>())
                .logins(new HashSet<>())
                .build();

        User savedUser = userRepository.save(newUser);

        assertAll(
                () -> assertEquals(newUser.getId(), savedUser.getId()),
                () -> assertEquals(newUser.getUsername(), savedUser.getUsername()),
                () -> assertEquals(newUser.getPassword(), savedUser.getPassword()),
                () -> assertEquals(newUser.getName(), savedUser.getName()),
                () -> assertEquals(newUser.getEmail(), savedUser.getEmail()),
                () -> assertEquals(newUser.getImage(), savedUser.getImage()),
                () -> assertEquals(newUser.getSurname(), savedUser.getSurname()),
                () -> assertEquals(newUser.getGender(), savedUser.getGender()),
                () -> assertEquals(newUser.getPhoneNumber(), savedUser.getPhoneNumber()),
                () -> assertEquals(newUser.getRoles(), savedUser.getRoles()),
                () -> assertEquals(newUser.getAppointments().size(), savedUser.getAppointments().size()),
                () -> assertEquals(newUser.getLogins().size(), savedUser.getLogins().size())
        );
    }

    @Test
    void update() {
        User newUser = User.builder()
                .id(UUID.randomUUID().toString())
                .username("poquito")
                .password("$2a$12$MvKeyxrlM0PxpT8egBnq1O/L/rY36QUWyhYDG7JINbziLpmYKmgDW")
                .name("poco")
                .email("poco@poco.com")
                .phoneNumber("609580881")
                .image(null)
                .gender(UserGender.Male)
                .surname("poquin")
                .roles(Set.of(UserRole.USER, UserRole.ADMIN))
                .appointments(new HashSet<>())
                .logins(new HashSet<>())
                .build();

        User savedUser = userRepository.save(newUser);

        User modUser = userRepository.getById(savedUser.getId());
        modUser.setPhoneNumber("090999909");

        userRepository.save(modUser);
        assertAll(
                () -> assertEquals(modUser.getId(), savedUser.getId()),
                () -> assertEquals(modUser.getUsername(), savedUser.getUsername()),
                () -> assertEquals(modUser.getPassword(), savedUser.getPassword()),
                () -> assertEquals(modUser.getName(), savedUser.getName()),
                () -> assertEquals(modUser.getEmail(), savedUser.getEmail()),
                () -> assertEquals(modUser.getImage(), savedUser.getImage()),
                () -> assertEquals(modUser.getSurname(), savedUser.getSurname()),
                () -> assertEquals(modUser.getGender(), savedUser.getGender()),
                () -> assertEquals(modUser.getPhoneNumber(), savedUser.getPhoneNumber()),
                () -> assertEquals(modUser.getRoles(), savedUser.getRoles()),
                () -> assertEquals(modUser.getAppointments().size(), savedUser.getAppointments().size()),
                () -> assertEquals(modUser.getLogins().size(), savedUser.getLogins().size())
        );
    }

    @Test
    void delete() {
        User newUser = User.builder()
                .id(UUID.randomUUID().toString())
                .username("poquiton")
                .password("$2a$12$MvKeyxrlM0PxpT8egBnq1O/L/rY36QUWyhYDG7JINbziLpmYKmgDW")
                .name("poco")
                .email("pocazo@poco.com")
                .phoneNumber("609580881")
                .image(null)
                .gender(UserGender.Male)
                .surname("poquin")
                .roles(Set.of(UserRole.USER, UserRole.ADMIN))
                .appointments(new HashSet<>())
                .logins(new HashSet<>())
                .build();

        entityManager.persist(newUser);
        entityManager.flush();

        userRepository.delete(newUser);
        assertNull(userRepository.findById(newUser.getId()).orElse(null));
    }


    @Test
    void findByUsernameIgnoreCase() {

    }

    @Test
    void findByUsernameContainsIgnoreCase() {

    }

    @Test
    void findByEmail() {

    }
}

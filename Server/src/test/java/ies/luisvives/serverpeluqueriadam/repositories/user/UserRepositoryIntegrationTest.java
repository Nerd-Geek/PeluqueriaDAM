package ies.luisvives.serverpeluqueriadam.repositories.user;

import ies.luisvives.serverpeluqueriadam.model.*;
import ies.luisvives.serverpeluqueriadam.repository.AppointmentRepository;
import ies.luisvives.serverpeluqueriadam.repository.LoginRepository;
import ies.luisvives.serverpeluqueriadam.repository.ServiceRepository;
import ies.luisvives.serverpeluqueriadam.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode =  DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserRepositoryIntegrationTest {

    private static final User user = User.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
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

    private static final Login login = Login.builder()
            .id("233149e4-b6f3-4692-ac71-2e8123bc24b3")
            .user(user)
            .instant(Date.from(Instant.now()))
            .token("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjMTMzNGQ1Ny0" +
                    "xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NjY4OTIsI" +
                    "mV4cCI6MTY0NjY1MzI5MiwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE" +
                    "1JTiJ9.8LxNLPBZF5xxmpVF3GRNyFOQCVnSGQlnOZiEW6BNZjb5VkvELVJR30Rc4BK4W" +
                    "ddlAivBy7dUEbArgy-EYgTW0w")
            .build();

    private static final Service service = Service.builder()
            .id("7dafe5fd-976b-450a-9bab-17ab450a4fff")
            .name("nombre")
            .stock(5)
            .price(2.5)
            .build();


    private static final Appointment appointment1 = Appointment.builder()
            .id("0fc7d018-9d32-11ec-b909-0242ac120002")
            .service(service)
            .user(user)
            .date(LocalDate.of(2022, 3, 15))
            .time(LocalTime.of(9, 0, 0))
            .build();

    private static final Appointment appointment2 = Appointment.builder()
            .id("0fc7d018-9d32-11ec-b909-0242ac120003")
            .service(service)
            .user(user)
            .date(LocalDate.of(2022, 3, 16))
            .time(LocalTime.of(10, 0, 0))
            .build();

    private static final Appointment appointment3 = Appointment.builder()
            .id("0fc7d018-9d32-11ec-b909-0242ac120004")
            .service(service)
            .user(user)
            .date(LocalDate.of(2022, 3, 17))
            .time(LocalTime.of(11, 0, 0))
            .build();


    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    @BeforeEach
    void setUp() {
        user.setAppointments(new HashSet<>());
        service.setAppointments(new HashSet<>());
        user.setLogins(new HashSet<>());
        userRepository.save(user);
        loginRepository.save(login);
        serviceRepository.save(service);
        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);
        appointmentRepository.save(appointment3);
        user.setAppointments(Set.of(appointment1, appointment2, appointment3));
        user.setLogins(Set.of(login));
        service.setAppointments(Set.of(appointment1, appointment2, appointment3));
        userRepository.save(user);
        serviceRepository.save(service);
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
                () -> assertEquals(savedUser.getId(), newUser.getId()),
                () -> assertEquals(savedUser.getUsername(), newUser.getUsername()),
                () -> assertEquals(savedUser.getPassword(), newUser.getPassword()),
                () -> assertEquals(savedUser.getName(), newUser.getName()),
                () -> assertEquals(savedUser.getEmail(), newUser.getEmail()),
                () -> assertEquals(savedUser.getImage(), newUser.getImage()),
                () -> assertEquals(savedUser.getSurname(), newUser.getSurname()),
                () -> assertEquals(savedUser.getGender(), newUser.getGender()),
                () -> assertEquals(savedUser.getPhoneNumber(), newUser.getPhoneNumber()),
                () -> assertEquals(savedUser.getRoles(), newUser.getRoles()),
                () -> assertEquals(savedUser.getAppointments().size(), newUser.getAppointments().size()),
                () -> assertEquals(savedUser.getLogins().size(), newUser.getLogins().size())
        );
    }

    @Test
    void findAll() {

        assertAll(
                () -> assertNotNull(userRepository.findAll()),
                () -> assertTrue(userRepository.findAll().stream().count() > 0)
        );
    }

    @Test
    void findById() {
        User savedUser = userRepository.findById(user.getId()).orElse(null);

        assertAll(
                () -> assertNotNull(savedUser),
                () -> assertEquals(savedUser.getId(), user.getId()),
                () -> assertEquals(savedUser.getUsername(), user.getUsername()),
                () -> assertEquals(savedUser.getPassword(), user.getPassword()),
                () -> assertEquals(savedUser.getName(), user.getName()),
                () -> assertEquals(savedUser.getEmail(), user.getEmail()),
                () -> assertEquals(savedUser.getImage(), user.getImage()),
                () -> assertEquals(savedUser.getSurname(), user.getSurname()),
                () -> assertEquals(savedUser.getGender(), user.getGender()),
                () -> assertEquals(savedUser.getPhoneNumber(), user.getPhoneNumber()),
                () -> assertEquals(savedUser.getRoles(), user.getRoles()),
                () -> assertEquals(savedUser.getAppointments().size(), user.getAppointments().size()),
                () -> assertEquals(savedUser.getLogins().size(), user.getLogins().size())
        );
    }

    @Test
    void update() {
        User newUser = User.builder()
                .id("ec272c62-9d31-11ec-b909-0242ac120002")
                .username("nombre usuario")
                .password("$2a$12$MvKeyxrlM0PxpT8egBnq1O/L/rY36QUWyhYDG7JINbziLpmYKmgDW")
                .name("poco")
                .email("poco@poco.com")
                .phoneNumber("609580881")
                .image(null)
                .gender(UserGender.Male)
                .surname("poquin")
                .roles(Set.of(UserRole.USER, UserRole.ADMIN))
                .appointments(user.getAppointments())
                .logins(user.getLogins())
                .build();
        User savedUser = userRepository.save(newUser);

        assertAll(
                () -> assertEquals(savedUser.getId(), newUser.getId()),
                () -> assertEquals(savedUser.getUsername(), newUser.getUsername()),
                () -> assertEquals(savedUser.getPassword(), newUser.getPassword()),
                () -> assertEquals(savedUser.getName(), newUser.getName()),
                () -> assertEquals(savedUser.getEmail(), newUser.getEmail()),
                () -> assertEquals(savedUser.getImage(), newUser.getImage()),
                () -> assertEquals(savedUser.getSurname(), newUser.getSurname()),
                () -> assertEquals(savedUser.getGender(), newUser.getGender()),
                () -> assertEquals(savedUser.getPhoneNumber(), newUser.getPhoneNumber()),
                () -> assertEquals(savedUser.getRoles(), newUser.getRoles()),
                () -> assertEquals(savedUser.getAppointments().size(), newUser.getAppointments().size()),
                () -> assertEquals(savedUser.getLogins().size(), newUser.getLogins().size())
        );
    }

    @Test
    void delete() {
        User newUser = User.builder()
                .id(UUID.randomUUID().toString())
                .username("ec272c62-9d31-11ec-b909-0242ac120042")
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
        newUser = userRepository.save(newUser);
        userRepository.delete(newUser);
        User begone = userRepository.findById(newUser.getId()).orElse(null);
        assertNull(begone);
    }


    @Test
    void findByUsernameIgnoreCase() {
        User savedUser = userRepository.findByUsernameIgnoreCase(user.getUsername().toUpperCase()).orElse(new User());

        assertAll(
                () -> assertEquals(savedUser.getId(), user.getId()),
                () -> assertEquals(savedUser.getUsername(), user.getUsername()),
                () -> assertEquals(savedUser.getPassword(), user.getPassword()),
                () -> assertEquals(savedUser.getName(), user.getName()),
                () -> assertEquals(savedUser.getEmail(), user.getEmail()),
                () -> assertEquals(savedUser.getImage(), user.getImage()),
                () -> assertEquals(savedUser.getSurname(), user.getSurname()),
                () -> assertEquals(savedUser.getGender(), user.getGender()),
                () -> assertEquals(savedUser.getPhoneNumber(), user.getPhoneNumber()),
                () -> assertEquals(savedUser.getRoles(), user.getRoles()),
                () -> assertEquals(savedUser.getAppointments().size(), user.getAppointments().size()),
                () -> assertEquals(savedUser.getLogins().size(), user.getLogins().size())
        );
    }

    @Test
    void findByUsernameContainsIgnoreCase() {
        List<User> allUsers = userRepository.findByUsernameContainsIgnoreCase("");
        List<User> oneUser = userRepository.findByUsernameContainsIgnoreCase("nombre usuario");
        List<User> noUser = userRepository.findByUsernameContainsIgnoreCase("1203894571-0293548120-9349857");
        List<User> findAll = userRepository.findAll();
        assertAll(
                () -> assertEquals(oneUser.get(0).getUsername(), "nombre usuario"),
                () -> assertEquals(allUsers.size(), findAll.size()),
                () -> assertEquals(noUser.size(), 0)
        );
    }

    @Test
    void findByEmail() {
        User emailUser = userRepository.findByEmail("adsada@sdasdd.com");
        User nonEmailUser  = userRepository.findByEmail("asdfasdfasdfasdfasdfasdfasdfasdf@sdasasdfasdfasdfasdfasdfasdfasdfasdfasddd.comnot");
        assertAll(
                () -> assertEquals(emailUser.getEmail(), "adsada@sdasdd.com"),
                () -> assertNull(nonEmailUser)
        );
    }
}

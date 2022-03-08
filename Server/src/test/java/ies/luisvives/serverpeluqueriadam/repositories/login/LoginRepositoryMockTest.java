package ies.luisvives.serverpeluqueriadam.repositories.login;

import ies.luisvives.serverpeluqueriadam.model.Login;
import ies.luisvives.serverpeluqueriadam.model.User;
import ies.luisvives.serverpeluqueriadam.model.UserGender;
import ies.luisvives.serverpeluqueriadam.repository.LoginRepository;
import ies.luisvives.serverpeluqueriadam.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginRepositoryMockTest {
    @MockBean
    private LoginRepository loginRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user.setAppointments(null);
        userRepository.save(user);
    }
    private final User user = User.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .username("nombre usuario")
            .name("nombre")
            .surname("apellido")
            .phoneNumber("912345678")
            .gender(UserGender.Male)
            .email("adsada@sdasdd.com")
            .password("asd")
            .build();

    private final Login login = Login.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .user(user)
            .token("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLC")
            .instant(Date.from(Instant.now()))
            .build();

    @Test
    @Order(1)
    void save() {
        Mockito.when(loginRepository.save(login)).thenReturn(login);
        Login res = loginRepository.save(login);
        assertAll(
                () -> assertEquals(login, res),
                () -> assertEquals(login.getId(), res.getId()),
                () -> assertEquals(login.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(login.getInstant(), res.getInstant()),
                () -> assertEquals(login.getToken(), res.getToken())
        );

        Mockito.verify(loginRepository, Mockito.times(1)).save(login);
    }


    @Test
    @Order(2)
    void findById() {
        Mockito.when(loginRepository.findById(login.getId()))
                .thenReturn(java.util.Optional.of(login));
        var res = loginRepository.findById(login.getId()).get();
        assertAll(
                () -> assertEquals(login, res),
                () -> assertEquals(login.getId(), res.getId()),
                () -> assertEquals(login.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(login.getInstant(), res.getInstant()),
                () -> assertEquals(login.getToken(), res.getToken())
        );

        Mockito.verify(loginRepository, Mockito.times(1))
                .findById(login.getId());
    }

    @Test
    @Order(3)
    void findAll() {
        Mockito.when(loginRepository.findAll())
                .thenReturn(List.of(login));
        List<Login> res = loginRepository.findAll();
        assertAll(
                () -> assertEquals(List.of(login), res),
                () -> assertEquals(login.getId(), res.get(0).getId()),
                () -> assertEquals(login.getUser().getUsername(), res.get(0).getUser().getUsername()),
                () -> assertEquals(login.getInstant(), res.get(0).getInstant()),
                () -> assertEquals(login.getToken(), res.get(0).getToken())
        );

        Mockito.verify(loginRepository, Mockito.times(1))
                .findAll();
    }

    @Test
    @Order(4)
    void update() {
        Mockito.when(loginRepository.save(login))
                .thenReturn(login);
        var res = loginRepository.save(login);
        assertAll(
                () -> assertEquals(login, res),
                () -> assertEquals(login.getId(), res.getId()),
                () -> assertEquals(login.getId(), res.getId()),
                () -> assertEquals(login.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(login.getInstant(), res.getInstant()),
                () -> assertEquals(login.getToken(), res.getToken())
        );

        Mockito.verify(loginRepository, Mockito.times(1))
                .save(login);
    }

    @Test
    @Order(5)
    void delete() {
        Mockito.doNothing().when(loginRepository).delete(login);
        loginRepository.delete(login);
        Mockito.verify(loginRepository, Mockito.times(1))
                .delete(login);
    }
}

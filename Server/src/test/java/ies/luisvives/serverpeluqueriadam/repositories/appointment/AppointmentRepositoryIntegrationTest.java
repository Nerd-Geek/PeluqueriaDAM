package ies.luisvives.serverpeluqueriadam.repositories.appointment;

import ies.luisvives.serverpeluqueriadam.model.*;
import ies.luisvives.serverpeluqueriadam.repository.AppointmentRepository;
import ies.luisvives.serverpeluqueriadam.repository.ServiceRepository;
import ies.luisvives.serverpeluqueriadam.repository.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode =  DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class AppointmentRepositoryIntegrationTest {
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

    private final Service service = Service.builder()
            .id("077fb088-9d32-11ec-b909-0242ac120002 \n")
            .name("nombre")
            .stock(5)
            .price(2.5)
            .build();


    private final Appointment appointment = Appointment.builder()
            .id("0fc7d018-9d32-11ec-b909-0242ac120002")
            .service(service)
            .user(user)
            .build();

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    @Order(1)
    void save() {
        userRepository.save(user);
        serviceRepository.save(service);
        Appointment ap = appointmentRepository.save(appointment);

        assertAll(
                () -> assertNotNull(ap),
                () -> assertEquals(appointment.getService().getName(), ap.getService().getName()),
                () -> assertEquals(appointment.getUser().getUsername(), ap.getUser().getUsername())
        );
    }
}

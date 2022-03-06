package ies.luisvives.serverpeluqueriadam.repositories.appointment;

import ies.luisvives.serverpeluqueriadam.model.*;
import ies.luisvives.serverpeluqueriadam.repository.AppointmentRepository;
import ies.luisvives.serverpeluqueriadam.repository.ServiceRepository;
import ies.luisvives.serverpeluqueriadam.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
            .id("7dafe5fd-976b-450a-9bab-17ab450a4fff")
            .name("nombre")
            .stock(5)
            .price(2.5)
            .build();


    private final Appointment appointment1 = Appointment.builder()
            .id("0fc7d018-9d32-11ec-b909-0242ac120002")
            .service(service)
            .user(user)
            .date(LocalDate.of(2022, 3, 15))
            .time(LocalTime.of(9, 0, 0))
            .build();

    private final Appointment appointment2 = Appointment.builder()
            .id("0fc7d018-9d32-11ec-b909-0242ac120003")
            .service(service)
            .user(user)
            .date(LocalDate.of(2022, 3, 16))
            .time(LocalTime.of(10, 0, 0))
            .build();

    private final Appointment appointment3 = Appointment.builder()
            .id("0fc7d018-9d32-11ec-b909-0242ac120004")
            .service(service)
            .user(user)
            .date(LocalDate.of(2022, 3, 17))
            .time(LocalTime.of(11, 0, 0))
            .build();


    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(user);
        serviceRepository.save(service);
    }

    @Test
    @Order(1)
    void save() {
        Appointment ap = appointmentRepository.save(appointment1);

        assertAll(
                () -> assertNotNull(ap),
                () -> assertEquals(appointment1.getService().getName(), ap.getService().getName()),
                () -> assertEquals(appointment1.getUser().getUsername(), ap.getUser().getUsername()),
                () -> assertEquals(appointment1.getTime(), ap.getTime()),
                () -> assertEquals(appointment1.getDate(), ap.getDate())
        );
    }

    @Test
    @Order(2)
    void findAll() {
        int size = appointmentRepository.findAll().size();
        appointmentRepository.save(appointment2);


        assertAll(
                () -> assertEquals(appointmentRepository.findAll().stream().count(), 1 + size),
                () -> assertNotNull(appointmentRepository.findAll())
        );
    }

    @Test
    @Order(3)
    void findById() {
        Appointment ap = appointmentRepository.save(appointment1);

        Appointment appointment = appointmentRepository.findById(ap.getId()).orElse(null);
        assertAll(
                () -> assertEquals(appointment.getId(), ap.getId()),
                () -> assertEquals(appointment.getDate(), ap.getDate()),
                () -> assertEquals(appointment.getTime(), ap.getTime()),
                () -> assertEquals(appointment.getUser().getUsername(), ap.getUser().getUsername()),
                () -> assertEquals(appointment.getService().getName(), ap.getService().getName())
        );
    }

    @Test
    @Order(4)
    void update() {
        appointment1.setTime(LocalTime.of(9, 0, 0));
        appointment1.setDate(LocalDate.of(2022, 3, 15));
        Appointment result = appointmentRepository.save(appointment1);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(appointment1.getDate(), result.getDate()),
                () -> assertEquals(appointment1.getTime(), result.getTime())
        );
    }

    @Test
    @Order(5)
    void delete() {
        Appointment ap = appointmentRepository.save(appointment1);
        appointmentRepository.delete(ap);
        ap = appointmentRepository.findById(appointment1.getId()).orElse(null);
        assertNull(ap);
    }

    @Test
    @Order(6)
    void findByDateAndTimeAndService_Id() {
        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);
        appointmentRepository.save(appointment3);

        List<Appointment> ap1List = appointmentRepository.findByDateAndTimeAndService_Id(
                appointment1.getDate()
                , appointment1.getTime()
                , appointment1.getService().getId()
        );

        assertAll(
                () -> assertEquals(ap1List.size(), 1)
        );

    }
}

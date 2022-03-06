package ies.luisvives.serverpeluqueriadam.repositories.appointment;

import ies.luisvives.serverpeluqueriadam.model.Appointment;
import ies.luisvives.serverpeluqueriadam.model.Service;
import ies.luisvives.serverpeluqueriadam.model.User;
import ies.luisvives.serverpeluqueriadam.model.UserGender;
import ies.luisvives.serverpeluqueriadam.repository.AppointmentRepository;
import ies.luisvives.serverpeluqueriadam.repository.ServiceRepository;
import ies.luisvives.serverpeluqueriadam.repository.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TypeExcludeFilters(value= DataJpaTypeExcludeFilter.class)
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@ImportAutoConfiguration
public class AppointmentRepositoryJPATest {

    private final User user = User.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120007")
            .username("nombre usuario")
            .name("nombre")
            .surname("apellido")
            .phoneNumber("912345678")
            .gender(UserGender.Male)
            .email("adsada@sdasdd.com")
            .password("asd")
            .build();

    private final Service service = Service.builder()
            .id("7dafe5fd-976b-450a-9bab-17ab450a4ffg")
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
    private TestEntityManager entityManager;

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;


    @Test
    void getAll() {
        long count = appointmentRepository.count();
        entityManager.persist(user);
        entityManager.persist(service);
        entityManager.persist(appointment1);
        entityManager.flush();
        assertAll (
                () -> assertEquals(appointmentRepository.findAll().size(), count + 1),
                () -> assertTrue(appointmentRepository.findAll().size() > 0)
        );
    }

    @Test
    void getById() {
        entityManager.persist(user);
        entityManager.persist(service);
        entityManager.persist(appointment1);
        entityManager.flush();
        Appointment appointment = appointmentRepository.findById(appointment1.getId()).orElse(null);
        assertAll(
                () -> assertEquals(appointment.getId(), appointment1.getId()),
                () -> assertEquals(appointment.getDate(), appointment1.getDate()),
                () -> assertEquals(appointment.getTime(), appointment1.getTime()),
                () -> assertEquals(appointment.getUser().getUsername(), appointment1.getUser().getUsername()),
                () -> assertEquals(appointment.getService().getName(), appointment1.getService().getName())
        );
    }

    @Test
    void save() {
        entityManager.persist(user);
        entityManager.persist(service);
        entityManager.flush();
        Appointment appointment = appointmentRepository.save(appointment1);

        assertAll(
                () -> assertEquals(appointment.getId(), appointment1.getId()),
                () -> assertEquals(appointment.getDate(), appointment1.getDate()),
                () -> assertEquals(appointment.getTime(), appointment1.getTime()),
                () -> assertEquals(appointment.getUser().getUsername(), appointment1.getUser().getUsername()),
                () -> assertEquals(appointment.getService().getName(), appointment1.getService().getName())
        );
    }

    @Test
    void update() {
        entityManager.persist(user);
        entityManager.persist(service);
        entityManager.persist(appointment2);
        entityManager.flush();

        appointment2.setDate(appointment2.getDate().plusDays(1));
        Appointment appointment = appointmentRepository.save(appointment2);

        assertAll(
                () -> assertEquals(appointment.getId(), appointment2.getId()),
                () -> assertEquals(appointment.getDate(), appointment2.getDate()),
                () -> assertEquals(appointment.getTime(), appointment2.getTime()),
                () -> assertEquals(appointment.getUser().getUsername(), appointment2.getUser().getUsername()),
                () -> assertEquals(appointment.getService().getName(), appointment2.getService().getName())
        );
    }

    @Test
    void delete() {
        entityManager.persist(user);
        entityManager.persist(service);
        entityManager.persist(appointment3);
        entityManager.flush();

        appointmentRepository.delete(appointment3);
        assertNull(appointmentRepository.findById(appointment3.getId()).orElse(null));
    }

    @Test
    void filteredFindAll() {
        entityManager.persist(user);
        entityManager.persist(service);
        entityManager.persist(appointment1);
        entityManager.persist(appointment2);
        entityManager.persist(appointment3);

        List<Appointment> appointments = appointmentRepository.findByDateAndTimeAndService_Id(appointment1.getDate(), appointment1.getTime(), appointment1.getService().getId());
        assertEquals(appointments.size(), 1);

    }
}

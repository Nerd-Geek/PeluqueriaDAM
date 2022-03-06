package ies.luisvives.serverpeluqueriadam.repositories.appointment;

import ies.luisvives.serverpeluqueriadam.model.Appointment;
import ies.luisvives.serverpeluqueriadam.model.Service;
import ies.luisvives.serverpeluqueriadam.model.User;
import ies.luisvives.serverpeluqueriadam.model.UserGender;
import ies.luisvives.serverpeluqueriadam.repository.AppointmentRepository;
import ies.luisvives.serverpeluqueriadam.repository.ServiceRepository;
import ies.luisvives.serverpeluqueriadam.repository.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AppointmentRepositoryMockTest {

    @MockBean
    private ServiceRepository serviceRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AppointmentRepository appointmentRepository;

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

    @Test
    void findAll() {
        Mockito.when(appointmentRepository.findAll()).thenReturn(List.of(appointment1, appointment2, appointment3));
        List<Appointment> appointments = appointmentRepository.findAll();
        assertAll(
                () -> assertEquals(appointments.size(), 3)
        );
        Mockito.verify(appointmentRepository, Mockito.times(1)).findAll();
    }

    @Test
    void findById() {
        Mockito.when(appointmentRepository.findById(appointment1.getId())).thenReturn(Optional.of(appointment1));

        assertEquals(appointmentRepository.findById(appointment1.getId()).orElse(null), appointment1);
        Mockito.verify(appointmentRepository, Mockito.times(1)).findById(appointment1.getId());
    }

    @Test
    void save() {
        Mockito.when(appointmentRepository.save(appointment1)).thenReturn(appointment1);

        assertEquals(appointmentRepository.save(appointment1), appointment1);
        Mockito.verify(appointmentRepository, Mockito.times(1)).save(appointment1);
    }

    @Test
    void delete() {
        Mockito.doNothing().when(appointmentRepository).delete(appointment1);
        appointmentRepository.delete(appointment1);
        Mockito.verify(appointmentRepository, Mockito.times(1)).delete(appointment1);
    }
}

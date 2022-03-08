package ies.luisvives.serverpeluqueriadam.controllers.appointment;

import ies.luisvives.serverpeluqueriadam.controller.AppointmentController;
import ies.luisvives.serverpeluqueriadam.dto.appointment.AppointmentDTO;
import ies.luisvives.serverpeluqueriadam.dto.appointment.CreateAppointmentDTO;
import ies.luisvives.serverpeluqueriadam.dto.service.ServiceDTO;
import ies.luisvives.serverpeluqueriadam.dto.user.UserDTO;
import ies.luisvives.serverpeluqueriadam.exceptions.ServiceNotFoundException;
import ies.luisvives.serverpeluqueriadam.exceptions.appointment.AppointmentNotFoundException;
import ies.luisvives.serverpeluqueriadam.mapper.AppointmentMapper;
import ies.luisvives.serverpeluqueriadam.mapper.ServiceMapper;
import ies.luisvives.serverpeluqueriadam.model.Appointment;
import ies.luisvives.serverpeluqueriadam.model.Service;
import ies.luisvives.serverpeluqueriadam.model.User;
import ies.luisvives.serverpeluqueriadam.model.UserGender;
import ies.luisvives.serverpeluqueriadam.repository.ServiceRepository;
import ies.luisvives.serverpeluqueriadam.repository.UserRepository;
import ies.luisvives.serverpeluqueriadam.services.appointments.AppointmentService;
import ies.luisvives.serverpeluqueriadam.services.uploads.StorageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class AppointmentControllerMockTest {


    @MockBean
    private AppointmentService appointmentService;

    @MockBean
    private AppointmentMapper appointmentMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ServiceRepository serviceRepository;

    @InjectMocks
    private AppointmentController appointmentController;

    @Autowired
    public AppointmentControllerMockTest(AppointmentService appointmentService, AppointmentMapper appointmentMapper, UserRepository userRepository, ServiceRepository serviceRepository) {
        this.appointmentService = appointmentService;
        this.appointmentMapper = appointmentMapper;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
    }

    User user = User.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120007")
            .username("nombre usuario")
            .name("nombre")
            .surname("apellido")
            .phoneNumber("912345678")
            .gender(UserGender.Male)
            .email("adsada@sdasdd.com")
            .password("asd")
            .build();

    UserDTO userDTO = UserDTO.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120007")
            .username("nombre usuario")
            .name("nombre")
            .surname("apellido")
            .phoneNumber("912345678")
            .gender(UserGender.Male)
            .email("adsada@sdasdd.com")
            .build();

    Service service = Service.builder()
            .id("7dafe5fd-976b-450a-9bab-17ab450a4ffg")
            .name("nombre")
            .stock(5)
            .price(2.5)
            .build();

    ServiceDTO serviceDTO = ServiceDTO.builder()
            .id("7dafe5fd-976b-450a-9bab-17ab450a4ffg")
            .name("nombre")
            .stock(5)
            .price(2.5)
            .build();


    User user2 = User.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120008")
            .username("nombre usuario2")
            .name("nombre")
            .surname("apellido")
            .phoneNumber("912345678")
            .gender(UserGender.Male)
            .email("adsada@sdasdd.com")
            .password("asd")
            .build();

    UserDTO userDTO2 = UserDTO.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120008")
            .username("nombre usuario2")
            .name("nombre")
            .surname("apellido")
            .phoneNumber("912345678")
            .gender(UserGender.Male)
            .email("adsada@sdasdd.com")
            .build();

    Service service2 = Service.builder()
            .id("7dafe5fd-976b-450a-9bab-17ab450a4ffh")
            .name("servicio2")
            .stock(5)
            .price(2.5)
            .build();

    ServiceDTO serviceDTO2 = ServiceDTO.builder()
            .id("7dafe5fd-976b-450a-9bab-17ab450a4ffh")
            .name("servicio2")
            .stock(5)
            .price(2.5)
            .build();

    Appointment appointment1 = Appointment.builder()
            .id("0fc7d018-9d32-11ec-b909-0242ac120002")
            .service(service)
            .user(user)
            .date(LocalDate.of(2022, 3, 15))
            .time(LocalTime.of(9, 0, 0))
            .build();

    AppointmentDTO appointmentDTO1 = new AppointmentDTO(
            "0fc7d018-9d32-11ec-b909-0242ac120002"
            , LocalDate.of(2022, 3, 15)
            , LocalTime.of(9, 0, 0)
            , userDTO
            , serviceDTO
    );

    Appointment appointment2 = Appointment.builder()
            .id("0fc7d018-9d32-11ec-b909-0242ac120006")
            .service(service2)
            .user(user2)
            .date(LocalDate.of(2022, 3, 16))
            .time(LocalTime.of(9, 0, 0))
            .build();

    AppointmentDTO appointmentDTO2 = new AppointmentDTO(
            "0fc7d018-9d32-11ec-b909-0242ac120006"
            , LocalDate.of(2022, 3, 16)
            , LocalTime.of(9, 0, 0)
            , userDTO2
            , serviceDTO2
    );

    @Test
    void findAll() {
        Mockito.when(appointmentService.findAllAppointments()).thenReturn(List.of(appointment1));

        Mockito.when(appointmentMapper.toDTO(List.of(appointment1))).thenReturn(List.of(appointmentDTO1));
        assertEquals(
                appointmentController.findAll(Optional.empty(), Optional.empty(), Optional.empty())
                , ResponseEntity.ok(List.of(appointmentDTO1)));

        Mockito.verify(appointmentService, Mockito.times(1)).findAllAppointments();
        Mockito.verify(appointmentMapper, Mockito.times(1)).toDTO(List.of(appointment1));
    }

    @Test
    void findAllFiltered() {
        Mockito.when(appointmentService.findAllAppointments()).thenReturn(List.of(appointment1, appointment2));

        Mockito.when(appointmentMapper.toDTO(List.of(appointment1, appointment2))).thenReturn(List.of(appointmentDTO1, appointmentDTO2));
        Mockito.when(appointmentMapper.toDTO(List.of(appointment1))).thenReturn(List.of(appointmentDTO1));
        Mockito.when(appointmentMapper.toDTO(List.of(appointment2))).thenReturn(List.of(appointmentDTO2));

        assertAll(
                () -> assertEquals(
                        ResponseEntity.ok(List.of(appointmentDTO2))
                        , appointmentController.findAll(Optional.empty(), Optional.of("2022-03-16"), Optional.empty())
                ),
                () -> assertEquals(

                        ResponseEntity.ok(List.of(appointmentDTO1))
                        , appointmentController.findAll(Optional.empty(), Optional.empty(), Optional.of("7dafe5fd-976b-450a-9bab-17ab450a4ffg"))
                ),
                () -> assertEquals(
                        ResponseEntity.ok(List.of(appointmentDTO2))
                        , appointmentController.findAll(Optional.of("nombre usuario2"), Optional.empty(), Optional.empty())
                ),
                () -> assertEquals(
                        ResponseEntity.ok(List.of(appointmentDTO2))
                        , appointmentController.findAll(Optional.of("nombre usuario2"), Optional.of("2022-03-16"), Optional.of("7dafe5fd-976b-450a-9bab-17ab450a4ffh"))
                )
        );

        Mockito.verify(appointmentService, Mockito.times(4)).findAllAppointments();
        Mockito.verify(appointmentMapper, Mockito.times(1)).toDTO(List.of(appointment1));
        Mockito.verify(appointmentMapper, Mockito.times(3)).toDTO(List.of(appointment2));
    }

    @Test
    void findById() {
        Mockito.when(appointmentService.findAppointmentById(appointment1.getId())).thenReturn(Optional.of(appointment1));

        Mockito.when(appointmentMapper.toDTO(appointment1)).thenReturn(appointmentDTO1);

        Mockito.when(appointmentService.findAppointmentById("notanID")).thenReturn(Optional.empty());

        Exception ex = assertThrows(AppointmentNotFoundException.class, () -> {
            appointmentController.findById("notanID");
        });
        assertAll(
                () -> assertEquals(
                        ResponseEntity.ok(appointmentDTO1)
                        , appointmentController.findById(appointment1.getId())
                ),
                () -> assertTrue(ex.getMessage().contains("notanID"))
        );
        Mockito.verify(appointmentService, Mockito.times(1)).findAppointmentById(appointment1.getId());
        Mockito.verify(appointmentService, Mockito.times(1)).findAppointmentById("notanID");
        Mockito.verify(appointmentMapper, Mockito.times(1)).toDTO(appointment1);
    }

    @Test
    void save() {
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO();
        createAppointmentDTO.setId(appointmentDTO1.getId());
        createAppointmentDTO.setDate(appointmentDTO1.getDate());
        createAppointmentDTO.setTime(appointmentDTO1.getTime());
        createAppointmentDTO.setUserId(appointmentDTO1.getUser().getId());
        createAppointmentDTO.setServiceId(appointmentDTO1.getService().getId());

        Mockito.when(appointmentService.saveAppointment(any(Appointment.class))).thenReturn(appointment1);
        Mockito.when(userRepository.findById(createAppointmentDTO.getUserId())).thenReturn(Optional.of(appointment1.getUser()));
        Mockito.when(serviceRepository.findById(createAppointmentDTO.getServiceId())).thenReturn(Optional.of(appointment1.getService()));

        Mockito.when(appointmentMapper.toDTO(appointment1)).thenReturn(appointmentDTO1);

        assertEquals(ResponseEntity.ok(appointmentDTO1), appointmentController.save(createAppointmentDTO));


        Mockito.verify(appointmentService, Mockito.times(1)).saveAppointment(any(Appointment.class));
        Mockito.verify(appointmentMapper, Mockito.times(1)).toDTO(any(Appointment.class));
        Mockito.verify(userRepository, Mockito.times(1)).findById(appointment1.getUser().getId());
        Mockito.verify(serviceRepository, Mockito.times(1)).findById(appointment1.getService().getId());
    }

    @Test
    void update() {
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO();
        createAppointmentDTO.setId(appointmentDTO1.getId());
        createAppointmentDTO.setDate(appointmentDTO1.getDate());
        createAppointmentDTO.setTime(appointmentDTO1.getTime());
        createAppointmentDTO.setUserId(appointmentDTO1.getUser().getId());
        createAppointmentDTO.setServiceId(appointmentDTO1.getService().getId());

        Mockito.when(appointmentService.findAppointmentById(createAppointmentDTO.getId())).thenReturn(Optional.of(appointment1));
        Mockito.when(appointmentService.updateAppointment(any(Appointment.class))).thenReturn(appointment1);
        Mockito.when(userRepository.findById(createAppointmentDTO.getUserId())).thenReturn(Optional.of(appointment1.getUser()));
        Mockito.when(serviceRepository.findById(createAppointmentDTO.getServiceId())).thenReturn(Optional.of(appointment1.getService()));

        Mockito.when(appointmentMapper.toDTO(appointment1)).thenReturn(appointmentDTO1);

        assertEquals(ResponseEntity.ok(appointmentDTO1), appointmentController.update(createAppointmentDTO.getId(), createAppointmentDTO));

        Mockito.verify(appointmentService, Mockito.times(1)).findAppointmentById(createAppointmentDTO.getId());
        Mockito.verify(appointmentService, Mockito.times(1)).updateAppointment(any(Appointment.class));
        Mockito.verify(appointmentMapper, Mockito.times(1)).toDTO(any(Appointment.class));
        Mockito.verify(userRepository, Mockito.times(1)).findById(appointment1.getUser().getId());
        Mockito.verify(serviceRepository, Mockito.times(1)).findById(appointment1.getService().getId());
    }

    @Test
    void delete() {
        Mockito.when(appointmentService.deleteAppointment(any(Appointment.class))).thenReturn(appointment1);
        Mockito.when(appointmentService.findAppointmentById(appointment1.getId())).thenReturn(Optional.of(appointment1));
        Mockito.when(appointmentMapper.toDTO(appointment1)).thenReturn(appointmentDTO1);

        assertEquals(appointmentController.delete(appointment1.getId()), ResponseEntity.ok(appointmentDTO1));

        Mockito.verify(appointmentService, Mockito.times(1)).findAppointmentById(appointment1.getId());
        Mockito.verify(appointmentService, Mockito.times(1)).deleteAppointment(any(Appointment.class));
        Mockito.verify(appointmentMapper, Mockito.times(1)).toDTO(any(Appointment.class));
    }
}

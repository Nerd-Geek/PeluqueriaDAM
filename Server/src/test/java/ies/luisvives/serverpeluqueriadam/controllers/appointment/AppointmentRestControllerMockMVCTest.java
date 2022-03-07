package ies.luisvives.serverpeluqueriadam.controllers.appointment;
import com.fasterxml.jackson.databind.ObjectMapper;
import ies.luisvives.serverpeluqueriadam.dto.appointment.AppointmentDTO;
import ies.luisvives.serverpeluqueriadam.dto.appointment.CreateAppointmentDTO;
import ies.luisvives.serverpeluqueriadam.dto.service.ServiceDTO;
import ies.luisvives.serverpeluqueriadam.dto.user.UserDTO;
import ies.luisvives.serverpeluqueriadam.mapper.AppointmentMapper;
import ies.luisvives.serverpeluqueriadam.model.Appointment;
import ies.luisvives.serverpeluqueriadam.model.Service;
import ies.luisvives.serverpeluqueriadam.model.User;
import ies.luisvives.serverpeluqueriadam.model.UserGender;
import ies.luisvives.serverpeluqueriadam.services.appointments.AppointmentService;
import ies.luisvives.serverpeluqueriadam.services.uploads.StorageService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppointmentRestControllerMockMVCTest {

    private final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private final AppointmentService appointmentService;
    @MockBean
    private final StorageService storageService;
    @MockBean
    private final AppointmentMapper appointmentMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<CreateAppointmentDTO> jsonCreateAppointmentDTO;
    @Autowired
    private JacksonTester<AppointmentDTO> jsonAppointmentDTO;

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

    Appointment appointment = Appointment.builder()
            .id("0fc7d018-9d32-11ec-b909-0242ac120002")
            .service(service)
            .user(user)
            .date(LocalDate.of(2022, 3, 15))
            .time(LocalTime.of(9, 0, 0))
            .build();

    AppointmentDTO appointmentDTO = new AppointmentDTO(
            "0fc7d018-9d32-11ec-b909-0242ac120002"
            , LocalDate.of(2022, 3, 15)
            , LocalTime.of(9, 0, 0)
            , userDTO
            , serviceDTO
    );

    @Autowired
    public AppointmentRestControllerMockMVCTest(AppointmentService appointmentService, StorageService storageService, AppointmentMapper appointmentMapper) {
        this.appointmentService = appointmentService;
        this.storageService = storageService;
        this.appointmentMapper = appointmentMapper;
    }

    @Test
    @Order(1)
    void findAllTest() throws Exception {

        Mockito.when(appointmentService.findAllAppointments())
                .thenReturn(List.of(appointment));

        Mockito.when(appointmentMapper.toDTO(List.of(appointment)))
                .thenReturn(List.of(appointmentDTO));

        mockMvc
                .perform(
                        get("/rest/appointments/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9." +
                                        "eyJzdWIiOiJjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2ND" +
                                        "Y2NzI0NTksImV4cCI6MTY0Njc1ODg1OSwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJBRE1JTiw" +
                                        "gVVNFUiJ9.M3vf-Hmh0mUP8qXS767BU4hG4n-FX5-gIehQH9nmc6Elh0P8AnIeLSbJrAI7aQ-" +
                                        "LkOKhBU3TRXxI2CHN-QHpLw")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].date", is(appointment.getDate())))
                .andExpect(jsonPath("$[0].time", is(appointment.getTime())))
                .andReturn();

        Mockito.verify(appointmentService, Mockito.times(1)).findAllAppointments();
        Mockito.verify(appointmentMapper, Mockito.times(1)).toDTO(List.of(appointment));
    }

    @Test
    @Order(2)
    void findByIdlTest() throws Exception {
        Mockito.when(appointmentService.findAppointmentById(appointment.getId()))
                .thenReturn(Optional.of(appointment));

        Mockito.when(appointmentMapper.toDTO(appointment)).thenReturn(appointmentDTO);

        mockMvc.perform(
                        get("/rest/appointments/" + appointment.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9." +
                                        "eyJzdWIiOiJjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2ND" +
                                        "Y2NzI0NTksImV4cCI6MTY0Njc1ODg1OSwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJBRE1JTiw" +
                                        "gVVNFUiJ9.M3vf-Hmh0mUP8qXS767BU4hG4n-FX5-gIehQH9nmc6Elh0P8AnIeLSbJrAI7aQ-" +
                                        "LkOKhBU3TRXxI2CHN-QHpLw"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date", is(appointment.getDate())))
                .andExpect(jsonPath("$.time", is(appointment.getTime())))
                .andReturn();

        Mockito.verify(appointmentService, Mockito.times(1)).findAppointmentById(appointment.getId());
        Mockito.verify(appointmentMapper, Mockito.times(1)).toDTO(appointment);
    }

    @Test
    @Order(3)
    void findByIdExceptionTest() throws Exception {
        Mockito.when(appointmentService.findAppointmentById(appointment.getId()))
                .thenReturn(Optional.empty());
        mockMvc.perform(
                        get("/rest/appointments/" + appointment.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9." +
                                        "eyJzdWIiOiJjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2ND" +
                                        "Y2NzI0NTksImV4cCI6MTY0Njc1ODg1OSwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJBRE1JTiw" +
                                        "gVVNFUiJ9.M3vf-Hmh0mUP8qXS767BU4hG4n-FX5-gIehQH9nmc6Elh0P8AnIeLSbJrAI7aQ-" +
                                        "LkOKhBU3TRXxI2CHN-QHpLw"))
                .andExpect(status().isNotFound());
        Mockito.verify(appointmentService, Mockito.times(1)).findAppointmentById(appointment.getId());
    }
}

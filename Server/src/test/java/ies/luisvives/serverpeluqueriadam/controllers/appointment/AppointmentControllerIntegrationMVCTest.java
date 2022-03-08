package ies.luisvives.serverpeluqueriadam.controllers.appointment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ies.luisvives.serverpeluqueriadam.dto.appointment.AppointmentDTO;
import ies.luisvives.serverpeluqueriadam.dto.appointment.CreateAppointmentDTO;
import ies.luisvives.serverpeluqueriadam.dto.service.ServiceDTO;
import ies.luisvives.serverpeluqueriadam.dto.user.UserDTO;
import ies.luisvives.serverpeluqueriadam.model.Appointment;
import ies.luisvives.serverpeluqueriadam.model.Service;
import ies.luisvives.serverpeluqueriadam.model.User;
import ies.luisvives.serverpeluqueriadam.model.UserGender;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@EnableAutoConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppointmentControllerIntegrationMVCTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;
    Service service = Service.builder()
            .id("7dafe5fd-976b-450a-9bab-17ab450a4fff")
            .name("Corte pelo Hombre")
            .stock(4)
            .price(16.0)
            .build();

    User user = User.builder()
            .id("c1334d57-120b-437b-baef-cf5b5f68cc3e")
            .username("jbatista49")
            .name("Admin")
            .surname("freljorld")
            .phoneNumber("234567890")
            .gender(UserGender.Male)
            .email("porofernandez@freljorld.com")
            .password("Godlike")
            .build();

    Appointment appointment = Appointment.builder()
            .id("5b4dce42-9142-469e-93c0-70ff2a26f03c")
            .service(service)
            .user(user)
            .date(LocalDate.of(2022, 02, 13))
            .time(LocalTime.of(10, 00, 00))
            .build();

    @BeforeEach
    void setUp() {
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
    }

    @Autowired
    private JacksonTester<CreateAppointmentDTO> jsonCreateAppointmentDTO;
    @Autowired
    private JacksonTester<AppointmentDTO> jsonAppointmentDTO;

    @Test
    @Order(1)
    void findAllTest() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(
                        get("/rest/appointments/")
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9." +
                                        "eyJzdWIiOiJjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2ND" +
                                        "Y2NzI0NTksImV4cCI6MTY0Njc1ODg1OSwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJBRE1JTiw" +
                                        "gVVNFUiJ9.M3vf-Hmh0mUP8qXS767BU4hG4n-FX5-gIehQH9nmc6Elh0P8AnIeLSbJrAI7aQ-" +
                                        "LkOKhBU3TRXxI2CHN-QHpLw"))
                .andReturn().getResponse();
        List<AppointmentDTO> myObjects = Arrays.asList(mapper.readValue(
                response.getContentAsString(), AppointmentDTO[].class)
        );

        assertAll(
                () -> assertEquals(myObjects.get(0).getId(), appointment.getId()),
                () -> assertEquals(myObjects.get(0).getDate(), appointment.getDate()),
                () -> assertEquals(myObjects.get(0).getTime(), appointment.getTime()),
                () -> assertEquals(myObjects.get(0).getUser().getUsername(), appointment.getUser().getUsername()),
                () -> assertEquals(myObjects.get(0).getService().getName(), appointment.getService().getName())
        );
    }

    @Test
    @Order(2)
    void findByIdTest() throws Exception {

        var response = mockMvc.perform(
                        get("/rest/appointments/" + appointment.getId())
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9." +
                                        "eyJzdWIiOiJjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2ND" +
                                        "Y2NzI0NTksImV4cCI6MTY0Njc1ODg1OSwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJBRE1JTiw" +
                                        "gVVNFUiJ9.M3vf-Hmh0mUP8qXS767BU4hG4n-FX5-gIehQH9nmc6Elh0P8AnIeLSbJrAI7aQ-" +
                                        "LkOKhBU3TRXxI2CHN-QHpLw"))
                .andReturn().getResponse();

        AppointmentDTO res = jsonAppointmentDTO.parseObject(response.getContentAsString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(res.getDate(), appointment.getDate()),
                () -> assertEquals(res.getTime(), appointment.getTime()),
                () -> assertEquals(res.getUser().getUsername(), appointment.getUser().getUsername()),
                () -> assertEquals(res.getService().getName(), appointment.getService().getName())
        );
    }

    @Test
    @Order(3)
    void deleteTest() throws Exception {

        var response = mockMvc.perform(MockMvcRequestBuilders.delete("/rest/appointments/" + appointment.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9." +
                        "eyJzdWIiOiJjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2ND" +
                        "Y2NzI0NTksImV4cCI6MTY0Njc1ODg1OSwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJBRE1JTiw" +
                        "gVVNFUiJ9.M3vf-Hmh0mUP8qXS767BU4hG4n-FX5-gIehQH9nmc6Elh0P8AnIeLSbJrAI7aQ-" +
                        "LkOKhBU3TRXxI2CHN-QHpLw")).andReturn().getResponse();

        var res = jsonAppointmentDTO.parseObject(response.getContentAsString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(res.getDate(), appointment.getDate()),
                () -> assertEquals(res.getTime(), appointment.getTime()),
                () -> assertEquals(res.getUser().getUsername(), appointment.getUser().getUsername()),
                () -> assertEquals(res.getService().getName(), appointment.getService().getName())
        );
    }

}

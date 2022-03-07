package ies.luisvives.serverpeluqueriadam.controllers.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import ies.luisvives.serverpeluqueriadam.dto.login.LoginDTO;
import ies.luisvives.serverpeluqueriadam.dto.user.LoginUserDTO;
import ies.luisvives.serverpeluqueriadam.mapper.LoginMapper;
import ies.luisvives.serverpeluqueriadam.model.Login;
import ies.luisvives.serverpeluqueriadam.model.User;
import ies.luisvives.serverpeluqueriadam.model.UserGender;
import ies.luisvives.serverpeluqueriadam.repository.LoginRepository;
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
public class LoginRestControllerMockMVCTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private final LoginRepository loginRepository;
    @MockBean
    private final StorageService storageService;
    @MockBean
    private final LoginMapper loginMapper;

    private final User user = User.builder()
            .id("76b2071a-3f97-4666-bd76-3d3d38ca677d")
            .username("jbatista49")
            .name("Juan")
            .surname("Batista")
            .phoneNumber("678429049")
            .gender(UserGender.Male)
            .email("aocrigane0@slideshare.net")
            .password("t6YsKgG")
            .logins(null)
            .build();

    Login login = Login.builder()
            .id("233149e4-b6f3-4692-ac71-2e8123bc24b2")
            .user(user)
            .instant(null)
            .token("123213412")
            .build();

    LoginUserDTO loginUserDTO = LoginUserDTO.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .build();
    LoginDTO loginDTO = LoginDTO.builder()
            .id(login.getId())
            .user(loginUserDTO)
            .instance(login.getInstant())
            .token(login.getToken())
            .build();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<LoginDTO> jsonCreateLogin;
    @Autowired
    private JacksonTester<LoginDTO> jsonLogin;

    @Autowired
    public LoginRestControllerMockMVCTest(LoginRepository loginRepository, StorageService storageService, LoginMapper loginMapper) {
        this.loginRepository = loginRepository;
        this.storageService = storageService;
        this.loginMapper = loginMapper;
    }

    @Test
    @Order(1)
    public void findAllTest() throws Exception {

        Mockito.when(loginRepository.findAll())
                .thenReturn(List.of(login));

        Mockito.when(loginMapper.toDTO(List.of(login)))
                .thenReturn(List.of(loginDTO));

        mockMvc
                .perform(
                        get("/rest/logins/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].instance", is(login.getInstant())))
                .andExpect(jsonPath("$[0].token", is(login.getToken())))
                .andReturn();

        Mockito.verify(loginRepository, Mockito.times(1)).findAll();
        Mockito.verify(loginMapper, Mockito.times(1)).toDTO(List.of(login));
    }

    @Test
    @Order(2)
    void deleteTest() throws Exception {
        Mockito.when(loginRepository.findById(login.getId()))
                .thenReturn(Optional.of(login));

        Mockito.when(loginMapper.toDTO(login)).thenReturn(loginDTO);

        Mockito.doNothing().when(loginRepository).delete(login);

        mockMvc.perform(
                        delete("/rest/logins/" + login.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.instance", is(login.getInstant())))
                .andExpect(jsonPath("$.token", is(login.getToken())))
                .andReturn();

        Mockito.verify(loginRepository, Mockito.times(1))
                .findById(login.getId());
        Mockito.verify(loginRepository, Mockito.times(1))
                .delete(login);
        Mockito.verify(loginMapper, Mockito.times(1))
                .toDTO(login);
    }

    @Test
    @Order(3)
    void deleteExceptionTest() throws Exception {
        Mockito.when(loginRepository.findById(login.getId()))
                .thenReturn(Optional.empty());

        mockMvc.perform(
                        delete("/rest/logins/" + login.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isNotFound());

        Mockito.verify(loginRepository, Mockito.times(1))
                .findById(login.getId());
    }
}

package ies.luisvives.serverpeluqueriadam.controllers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ies.luisvives.serverpeluqueriadam.dto.service.ServiceDTO;
import ies.luisvives.serverpeluqueriadam.model.Service;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceRestControllerIntegrationMVCTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;

    Service service = Service.builder()
            .id("7dafe5fd-976b-450a-9bab-17ab450a4fff")
            .name("Corte pelo Hombre")
            .price(15.0)
            .stock(4)
            .build();
    @Autowired
    private JacksonTester<ServiceDTO> jsonCreateServiceDTO;
    @Autowired
    private JacksonTester<ServiceDTO> jsonServiceDTO;

    @Test
    @Order(1)
    void findAllTest() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(
                        get("/rest/services/all")
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andReturn().getResponse();

        ObjectMapper mapper = new ObjectMapper();
        List<ServiceDTO> myObjects = Arrays.asList(mapper.readValue(response.getContentAsString(), ServiceDTO[].class));

        assertAll(
                () -> assertEquals(myObjects.get(0).getId(), service.getId()),
                () -> assertEquals(myObjects.get(0).getName(), service.getName()),
                () -> assertEquals(myObjects.get(0).getPrice(), service.getPrice()),
                () -> assertEquals(myObjects.get(0).getStock(), service.getStock())
        );
    }

    @Test
    @Order(3)
    void saveTest() throws Exception {
        ServiceDTO serviceDTO = ServiceDTO.builder()
                .id(service.getId())
                .name(service.getName())
                .price(service.getPrice())
                .stock(service.getStock())
                .build();

        var response = mockMvc.perform(MockMvcRequestBuilders.post("/rest/services/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCreateServiceDTO.write(serviceDTO).getJson())
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg")).andReturn().getResponse();

        var res = jsonServiceDTO.parseObject(response.getContentAsString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(res.getName(), service.getName()),
                () -> assertEquals(res.getPrice(), service.getPrice()),
                () -> assertEquals(res.getStock(), service.getStock()),
                () -> assertEquals(res.getName(), serviceDTO.getName()),
                () -> assertEquals(res.getPrice(), serviceDTO.getPrice()),
                () -> assertEquals(res.getStock(), serviceDTO.getStock())
        );
    }

    @Test
    @Order(4)
    void updateTest() throws Exception {
        ServiceDTO serviceDTO = ServiceDTO.builder()
                .id(service.getId())
                .name(service.getName())
                .price(service.getPrice())
                .stock(service.getStock())
                .build();

        var response = mockMvc.perform(MockMvcRequestBuilders.put("/rest/services/" + service.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonServiceDTO.write(serviceDTO).getJson())
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg")).andReturn().getResponse();

        var res = jsonServiceDTO.parseObject(response.getContentAsString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(res.getName(), service.getName()),
                () -> assertEquals(res.getPrice(), service.getPrice()),
                () -> assertEquals(res.getStock(), service.getStock()),
                () -> assertEquals(res.getName(), serviceDTO.getName()),
                () -> assertEquals(res.getPrice(), serviceDTO.getPrice()),
                () -> assertEquals(res.getStock(), serviceDTO.getStock())
        );
    }

    @Test
    @Order(5)
    void deleteTest() throws Exception {

        var response = mockMvc.perform(MockMvcRequestBuilders.delete("/rest/services/" + service.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                        "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                        "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                        "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg")).andReturn().getResponse();

        var res = jsonServiceDTO.parseObject(response.getContentAsString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(res.getName(), service.getName()),
                () -> assertEquals(res.getPrice(), service.getPrice()),
                () -> assertEquals(res.getStock(), service.getStock())
        );
    }

    @Test
    @Order(6)
    void findAllAlternativeTest() throws Exception {
        mockMvc.perform(get("/rest/services/")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(service.getName())))
                .andExpect(jsonPath("$[0].price", is(service.getPrice())))
                .andExpect(jsonPath("$[0].stock", is(service.getStock())))
                .andReturn();
    }

    @Test
    @Order(7)
    void findByIdlternativeTest() throws Exception {
        mockMvc.perform(get("/rest/services/" + service.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(service.getName())))
                .andExpect(jsonPath("$.price", is(service.getPrice())))
                .andExpect(jsonPath("$.stock", is(service.getStock())))
                .andReturn();
    }

    @Test
    @Order(8)
    void postAlternativeTest() throws Exception {
        ServiceDTO serviceDTO = ServiceDTO.builder()
                .id(service.getId())
                .name(service.getName())
                .price(service.getPrice())
                .stock(service.getStock())
                .build();


        var json = jsonServiceDTO.write(serviceDTO).getJson();

        mockMvc.perform(post("/rest/services/")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(service.getName())))
                .andExpect(jsonPath("$.price", is(service.getPrice())))
                .andExpect(jsonPath("$.stock", is(service.getStock())))
                .andReturn();
    }

    @Test
    @Order(9)
    void updateAlternativeTest() throws Exception {
        ServiceDTO serviceDTO = ServiceDTO.builder()
                .name(service.getName())
                .price(service.getPrice())
                .stock(service.getStock())
                .build();

        var json = jsonServiceDTO.write(serviceDTO).getJson();

        mockMvc.perform(put("/rest/services/" + service.getId())
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(service.getName())))
                .andExpect(jsonPath("$.price", is(service.getPrice())))
                .andExpect(jsonPath("$.stock", is(service.getStock())))
                .andReturn();
    }

    @Test
    @Order(10)
    void deleteAlternativeTest() throws Exception {
        mockMvc.perform(delete("/rest/services/" + service.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(service.getName())))
                .andExpect(jsonPath("$.price", is(service.getPrice())))
                .andExpect(jsonPath("$.stock", is(service.getStock())))
                .andReturn();
    }
}

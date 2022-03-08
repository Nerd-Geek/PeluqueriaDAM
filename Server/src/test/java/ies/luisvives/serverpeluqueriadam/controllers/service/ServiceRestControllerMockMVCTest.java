package ies.luisvives.serverpeluqueriadam.controllers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ies.luisvives.serverpeluqueriadam.dto.service.ServiceDTO;
import ies.luisvives.serverpeluqueriadam.mapper.ServiceMapper;
import ies.luisvives.serverpeluqueriadam.model.Service;
import ies.luisvives.serverpeluqueriadam.repository.ServiceRepository;
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
public class ServiceRestControllerMockMVCTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private final ServiceRepository serviceRepository;
    @MockBean
    private final StorageService storageService;
    @MockBean
    private final ServiceMapper serviceMapper;
    private final Service service = Service.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .name("nombre")
            .price(2.0)
            .stock(2)
            .build();
    private final ServiceDTO serviceDTO = ServiceDTO.builder()
            .name(service.getName())
            .price(service.getPrice())
            .stock(service.getStock())
            .build();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<ServiceDTO> jsonCreateServiceDTO;
    @Autowired
    private JacksonTester<ServiceDTO> jsonServiceDTO;

    @Autowired
    public ServiceRestControllerMockMVCTest(ServiceRepository serviceRepository, StorageService storageService,
                                            ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.storageService = storageService;
        this.serviceMapper = serviceMapper;
    }

    @Test
    @Order(1)
    void findAllTest() throws Exception {
        Mockito.when(serviceRepository.findAll()).thenReturn(List.of(service));
        Mockito.when(serviceMapper.toDTO(List.of(service))).thenReturn(List.of(serviceDTO));

        mockMvc
                .perform(
                        get("/rest/services/all")
                                .contentType(MediaType.APPLICATION_JSON)
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
        Mockito.verify(serviceRepository, Mockito.times(2)).findAll();
        Mockito.verify(serviceMapper, Mockito.times(2)).toDTO(List.of(service));
    }

    @Test
    @Order(2)
    void findByIdTest() throws Exception {
        Mockito.when(serviceRepository.findById(service.getId()))
                .thenReturn(Optional.of(service));
        Mockito.when(serviceMapper.toDTO(service)).thenReturn(serviceDTO);
        mockMvc.perform(
                get("/rest/services/" + service.getId())
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
        Mockito.verify(serviceRepository, Mockito.times(1)).findById(service.getId());
        Mockito.verify(serviceMapper, Mockito.times(1)).toDTO(service);
    }

    @Test
    @Order(3)
    void findByExceptionTEst() throws Exception {
        Mockito.when(serviceRepository.findById(service.getId()))
                .thenReturn(Optional.empty());
        mockMvc.perform(
                get("/rest/services/" + service.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isNotFound());
        Mockito.verify(serviceRepository, Mockito.times(1)).findById(service.getId());
    }

    @Test
    @Order(4)
    void deleteTest() throws Exception {
        Mockito.when(serviceRepository.findById(service.getId()))
                .thenReturn(Optional.of(service));
        Mockito.when(serviceMapper.toDTO(service)).thenReturn(serviceDTO);
        Mockito.doNothing().when(serviceRepository).delete(service);

        mockMvc.perform(
                delete("/rest/services/" + service.getId())
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

        Mockito.verify(serviceRepository, Mockito.times(1))
                .findById(service.getId());
        Mockito.verify(serviceRepository, Mockito.times(1))
                .delete(service);
        Mockito.verify(serviceMapper, Mockito.times(1))
                .toDTO(service);
    }

    @Test
    @Order(5)
    void deleteExceptionTest() throws Exception {
        Mockito.when(serviceRepository.findById(service.getId()))
                .thenReturn(Optional.empty());
        mockMvc.perform(
                delete("/rest/services/" + service.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOi" +
                                "JjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NzY2MzgsImV4cCI6MT" +
                                "Y0NjY2MzAzOCwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE1JTiJ9.HIY1f7O_OWY9SDfnJHkgN" +
                                "GvqxqbWuJutdF_cnA7ulkwYz-LrDpAFrsFd9MFSQCL7Ms87cqALVqXsV4z0cphRYg"))
                .andExpect(status().isNotFound());
        Mockito.verify(serviceRepository, Mockito.times(1)).findById(service.getId());
    }

    @Test
    @Order(6)
    void updateTest() throws Exception {
        Mockito.when(serviceRepository.findById(service.getId())).thenReturn(Optional.of(service));
        Mockito.when(serviceRepository.save(service)).thenReturn(service);
        Mockito.when(serviceMapper.toDTO(service)).thenReturn(serviceDTO);
        var ser =  jsonServiceDTO.write(serviceDTO).getJson();

        mockMvc.perform(
                put("/rest/services/" + service.getId())
                        .content(ser)
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
        Mockito.verify(serviceRepository, Mockito.times(1)).findById(service.getId());
        Mockito.verify(serviceRepository, Mockito.times(1)).save(service);
        Mockito.verify(serviceMapper, Mockito.times(1)).toDTO(service);
    }
}
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

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
                                            ServiceMapper serviceMapper, MockMvc mockMvc) {
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
                        get("/rest/services/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())

                .andReturn();
    }
}

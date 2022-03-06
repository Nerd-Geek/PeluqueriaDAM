package ies.luisvives.serverpeluqueriadam.repositories.service;

import ies.luisvives.serverpeluqueriadam.model.Service;
import ies.luisvives.serverpeluqueriadam.repository.ServiceRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ServiceRepositoryIntegrationTest {
    private final Service service = Service.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .name("nombre")
            .price(2.0)
            .stock(2)
            .build();

    @Autowired
    private ServiceRepository serviceRepository;

    @Test
    @Order(1)
    void save() {
        Service ser = serviceRepository.save(service);

        assertAll(
                () -> assertNotNull(ser),
                () -> assertEquals(service.getName(), ser.getName()),
                () -> assertEquals(service.getPrice(), ser.getPrice()),
                () -> assertEquals(service.getStock(), ser.getStock())
        );
    }

    @Test
    @Order(2)
    void getAllService() {
        assertTrue(serviceRepository.findAll().size() > 0);
    }

    @Test
    @Order(3)
    void getServiceId() {
        Service serv = serviceRepository.save(service);
        Service ser = serviceRepository.findById(serv.getId()).get();
        assertAll(
                () -> assertNotNull(ser),
                () -> assertEquals(service.getName(), ser.getName()),
                () -> assertEquals(service.getPrice(), ser.getPrice()),
                () -> assertEquals(service.getStock(), ser.getStock())
        );
    }

    @Test
    @Order(4)
    void updateService() {
        Service ser = serviceRepository.save(service);
        ser = serviceRepository.findById(ser.getId()).get();
        ser.setName("Peluquería");

        Service serv = serviceRepository.save(ser);
        assertAll(
                () -> assertNotNull(serv),
                () -> assertEquals("Peluquería", serv.getName()),
                () -> assertEquals(service.getPrice(), serv.getPrice()),
                () -> assertEquals(service.getStock(), serv.getStock())
        );
    }

    @Test
    @Order(5)
    void deleteService() {
        Service ser = serviceRepository.save(service);
        ser = serviceRepository.findById(ser.getId()).get();

        serviceRepository.delete(ser);

        assertNull(serviceRepository.findById(ser.getId()).orElse(null));
    }
}

package ies.luisvives.serverpeluqueriadam.repositories.service;

import ies.luisvives.serverpeluqueriadam.model.Service;
import ies.luisvives.serverpeluqueriadam.repository.ServiceRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceRepositoryMockTest {

    private final Service service = Service.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .name("nombre")
            .price(2.0)
            .stock(2)
            .build();

    @MockBean
    private ServiceRepository serviceRepository;

    @Test
    @Order(1)
    void save() {
        Mockito.when(serviceRepository.save(service)).thenReturn(service);
        Service ser = serviceRepository.save(service);
        assertAll(
                () -> assertNotNull(ser),
                () -> assertEquals(service.getName(), ser.getName()),
                () -> assertEquals(service.getPrice(), ser.getPrice()),
                () -> assertEquals(service.getStock(), ser.getStock())
        );

        Mockito.verify(serviceRepository, Mockito.times(1)).save(service);
    }

    @Test
    @Order(2)
    void findById() {
        Mockito.when(serviceRepository.findById(service.getId()))
                .thenReturn(java.util.Optional.of(service));
        Service ser = serviceRepository.findById(service.getId()).get();
        assertAll(
                () -> assertEquals(service, ser),
                () -> assertEquals(service.getId(), ser.getId()),
                () -> assertEquals(service.getName(), ser.getName()),
                () -> assertEquals(service.getPrice(), ser.getPrice()),
                () -> assertEquals(service.getStock(), ser.getStock())
        );

        Mockito.verify(serviceRepository, Mockito.times(1)).findById(service.getId());
    }

    @Test
    @Order(3)
    void findAll() {
        Mockito.when(serviceRepository.findAll()).thenReturn(List.of(service));
        List<Service> ser = serviceRepository.findAll();
        assertAll(
                () -> assertEquals(List.of(service), ser),
                () -> assertEquals(service.getId(), ser.get(0).getId()),
                () -> assertEquals(service.getName(), ser.get(0).getName()),
                () -> assertEquals(service.getPrice(), ser.get(0).getPrice()),
                () -> assertEquals(service.getStock(), ser.get(0).getStock())
        );
        Mockito.verify(serviceRepository, Mockito.times(1)).findAll();
    }

    @Test
    @Order(4)
    void update() {
        Mockito.when(serviceRepository.save(service)).thenReturn(service);
        Service ser = serviceRepository.save(service);
        assertAll(
                () -> assertEquals(service, ser),
                () -> assertEquals(service.getId(), ser.getId()),
                () -> assertEquals(service.getName(), ser.getName()),
                () -> assertEquals(service.getPrice(), ser.getPrice()),
                () -> assertEquals(service.getStock(), ser.getStock())
        );
        Mockito.verify(serviceRepository, Mockito.times(1)).save(service);
    }

    @Test
    @Order(5)
    public void delete() {
        Mockito.doNothing().when(serviceRepository).delete(service);
        serviceRepository.delete(service);
        Mockito.verify(serviceRepository, Mockito.times(1)).delete(service);
    }
}

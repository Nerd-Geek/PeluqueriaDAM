package ies.luisvives.serverpeluqueriadam.repositories.service;

import ies.luisvives.serverpeluqueriadam.model.Service;
import ies.luisvives.serverpeluqueriadam.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TypeExcludeFilters(value= DataJpaTypeExcludeFilter.class)
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@ImportAutoConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceRepositoryJPATest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ServiceRepository serviceRepository;

    private final Service service = Service.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .name("nombre")
            .price(2.0)
            .stock(2)
            .build();

    @Test
    @Order(1)
    void getAllTest() {
        entityManager.persist(service);
        entityManager.flush();

        assertTrue(serviceRepository.findAll().size() > 0);
    }

    @Test
    @Order(2)
    void getByIdTest() {
        entityManager.persist(service);
        entityManager.flush();

        Service serviceFound = serviceRepository.findById(service.getId()).get();
        assertAll(
                () -> assertNotNull(serviceFound),
                () -> assertEquals(service.getName(), serviceFound.getName()),
                () -> assertEquals(service.getPrice(), serviceFound.getPrice()),
                () -> assertEquals(service.getStock(), serviceFound.getStock())
        );
    }

    @Test
    @Order(3)
    void findByNameContainsIgnoreCaseTest() {
        entityManager.persist(service);
        entityManager.flush();

        Service serviceFound = serviceRepository.findByNameContainsIgnoreCase(java.util.Optional.ofNullable(service.getName())).get(0);
        assertAll(
                () -> assertNotNull(serviceFound),
                () -> assertEquals(service.getName(), serviceFound.getName()),
                () -> assertEquals(service.getPrice(), serviceFound.getPrice()),
                () -> assertEquals(service.getStock(), serviceFound.getStock())
        );
    }

    @Test
    @Order(4)
    void save() {
        Service serv = serviceRepository.save(service);
        assertAll(
                () -> assertNotNull(serv),
                () -> assertEquals(service.getName(), serv.getName()),
                () -> assertEquals(service.getPrice(), serv.getPrice()),
                () -> assertEquals(service.getStock(), serv.getStock())
        );
    }

    @Test
    @Order(5)
    void update() {
        entityManager.persist(service);
        entityManager.flush();
        Service serviceFound = serviceRepository.findById(service.getId()).get();
        serviceFound.setName("Peluquería");
        serviceFound.setStock(5);
        Service updated = serviceRepository.save(serviceFound);
        assertAll(
                () -> assertNotNull(updated),
                () -> assertEquals(service.getName(), updated.getName()),
                () -> assertEquals(service.getPrice(), updated.getPrice()),
                () -> assertEquals(service.getStock(), updated.getStock())
        );
    }

    @Test
    @Order(6)
    void delete() {
        entityManager.persist(service);
        entityManager.flush();
        serviceRepository.delete(service);
        Service ser = serviceRepository.findById(service.getId()).orElse(null);
        assertNull(ser);
    }
}

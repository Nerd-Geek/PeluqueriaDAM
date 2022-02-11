package ies.luisvives.serverpeluqueriadam.repository;

import ies.luisvives.serverpeluqueriadam.model.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, String> {
    List<Service> findByName(String name);
    List<Service> findByOrderByPriceAsc(Sort sort);
}

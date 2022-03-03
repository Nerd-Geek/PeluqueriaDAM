package ies.luisvives.serverpeluqueriadam.repository;

import ies.luisvives.serverpeluqueriadam.model.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, String> {
    List<Service> findByName(Optional<String> name);

    List<Service> findByNameContainsIgnoreCase(Optional<String> name);



    List<Service> findByOrderByPriceAsc(Sort sort);
}

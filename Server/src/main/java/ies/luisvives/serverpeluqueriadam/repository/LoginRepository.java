package ies.luisvives.serverpeluqueriadam.repository;

import ies.luisvives.serverpeluqueriadam.model.Login;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface LoginRepository extends CrudRepository<Login,String> {
    Page<Login> findAllById(Pageable pageable, String id);
}

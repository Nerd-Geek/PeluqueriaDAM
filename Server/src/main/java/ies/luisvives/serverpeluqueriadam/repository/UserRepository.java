package ies.luisvives.serverpeluqueriadam.repository;

import java.util.List;
import java.util.Optional;

import ies.luisvives.serverpeluqueriadam.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    List<User> findByLastName(String lastName);

    Optional<User> findById(String id);
}

package ies.luisvives.serverpeluqueriadam.repository;

import ies.luisvives.serverpeluqueriadam.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);
}
